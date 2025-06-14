package com.example.csc3402_lab.librarymanagement.controller;

import com.example.csc3402_lab.librarymanagement.model.Book;
import com.example.csc3402_lab.librarymanagement.model.Borrow;
import com.example.csc3402_lab.librarymanagement.model.Borrower;
import com.example.csc3402_lab.librarymanagement.model.FineDTO;
import com.example.csc3402_lab.librarymanagement.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/borrower")
public class BorrowerController {
    private final BookService bookService;
    private final BorrowerService borrowerService;
    private final BorrowService borrowService;

    public BorrowerController(BorrowerService borrowerService, BookService bookService, BorrowService borrowService) {
        this.borrowerService = borrowerService;
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    //Show login page
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }

    // Show signup page
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("borrower", new Borrower());
        return "signup"; // returns signup.html
    }

    // Handle signup form POST
    @PostMapping("/signup")
    public String signupBorrower(@ModelAttribute Borrower borrower,
                                 @RequestParam String confirmPassword,
                                 Model model, RedirectAttributes redirectAttributes) {

        if (!borrower.getBorrowerPass().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }

        if (borrowerService.findByUsername(borrower.getBorrowerUsername()) != null) {
            model.addAttribute("usernameExists", true);
            return "signup";
        }

        borrowerService.saveBorrower(borrower);
        model.addAttribute("showSuccessPopup", true); // trigger modal
        return "signup";
    }

    // Forgot password - show form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // View: forgot-password.html
    }

    @PostMapping("/forgot-password")
    @ResponseBody
    public String handleForgotPassword(@RequestBody Borrower request) {
        Borrower borrower = borrowerService.findByBorrowerIdAndBorrowerUsername(
                request.getBorrowerId(), request.getBorrowerUsername());

        if (borrower != null) {
            String newRawPassword = generateRandomPassword(); // e.g., "Lib12345"
            borrower.setBorrowerPass(newRawPassword); // This gets encrypted in saveBorrower()
            borrowerService.saveBorrower(borrower);

            return "{\"reset\": true, \"newPassword\": \"" + newRawPassword + "\"}";
        } else {
            return "{\"reset\": false}";
        }
    }

    private String generateRandomPassword() {
        return "Lib" + (int) (Math.random() * 10000); // Example: Lib2943
    }


    @GetMapping("/mainpage")
    public String mainPage(Model model, Principal principal) {
        String username = principal.getName();
        Borrower borrower = borrowerService.findByUsername(username);

        if (borrower == null) {
            return "redirect:/borrower/login";
        }

        List<Borrow> borrowedBooks = borrowService.findByBorrower(borrower);
        model.addAttribute("borrowedBooks", borrowedBooks); // add this line
        model.addAttribute("totalBooks", borrowedBooks.size());
        model.addAttribute("borrower", borrower);

        return "mainpage";
    }


    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String username = principal.getName();
        Borrower borrower = borrowerService.findByUsername(username);
        model.addAttribute("borrower", borrower);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("borrower") Borrower formBorrower,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Borrower existingBorrower = userDetails.getBorrower();

        // Update only editable fields
        existingBorrower.setBorrowerEmail(formBorrower.getBorrowerEmail());
        existingBorrower.setBorrowerPhoneNo(formBorrower.getBorrowerPhoneNo());

        // Save the updated borrower
        borrowerService.saveBorrower(existingBorrower);

        // Add success message
        redirectAttributes.addFlashAttribute("showSuccessPopup", true);
        return "redirect:/borrower/profile";
    }

    @GetMapping("/fines")
    public String checkFinePage(Model model, Principal principal) {
        String username = principal.getName();
        Borrower borrower = borrowerService.findByUsername(username);
        List<Borrow> borrows = borrowService.findByBorrower(borrower);

        List<FineDTO> fines = borrows.stream()
                .filter(b -> b.getDaysOverdue() > 0 && b.getReturnDate() == null)
                .map(b -> new FineDTO(
                        b.getBorrowId(),
                        b.getBook().getBookTitle(),
                        b.getBook().getBookAuthor(),
                        b.getDaysOverdue()))
                .toList();

        model.addAttribute("fines", fines);
        return "check_fine"; // this must match exactly with check_fine.html filename
    }

    @GetMapping("/fines/details")
    public String showFineDetails(@RequestParam("fineId") Long fineId, Model model) {
        Optional<Borrow> optionalBorrow = borrowService.getBorrowById(fineId);
        if (optionalBorrow.isEmpty()) {
            model.addAttribute("totalFineAmount", 0.0);
            return "fine_details_view";
        }
        Borrow borrow = optionalBorrow.get();
        if (borrow == null) {
            model.addAttribute("totalFineAmount", 0.0);
            return "fine_details_view";
        }

        // Basic fine calculation logic (you can adjust logic as needed)
        long daysOverdue = borrow.getDaysOverdue();
        double finePerDay = 1.00; // RM 1.00 per day
        double totalFine = daysOverdue * finePerDay;

        // Prepare fine detail row for table
        FineDTO fineDetail = new FineDTO(
                borrow.getBorrowId(),
                borrow.getBook().getBookTitle(),
                borrow.getBook().getBookAuthor(),
                daysOverdue
        );

        // Additional fields if needed in table
        model.addAttribute("fineDetails", List.of(new Object() {
            public final String bookTitle = borrow.getBook().getBookTitle();
            public final String bookAuthor = borrow.getBook().getBookAuthor();
            public final LocalDate borrowDate = borrow.getBorrowDate();
            public final LocalDate dueDate = borrow.getDueDate();
            public final LocalDate returnedDate = borrow.getReturnDate();
            public final long daysOverdue = borrow.getDaysOverdue();
            public final double fineAmountPerBook = totalFine;
        }));

        model.addAttribute("totalFineAmount", totalFine);

        return "fine_details_view";
    }

    @GetMapping("/store/local")
    public String showLocalBookForm(Model model) {
        model.addAttribute("book", new Book()); // for new form
        return "local_books";
    }

    @PostMapping("/store/local")
    public String saveLocalBook(@ModelAttribute Book book,
                                Model model,
                                HttpSession session,
                                Principal principal) {

        Borrower borrower = borrowerService.findByUsername(principal.getName());

        // Check if this borrower already borrowed the book with the same ISBN
        List<Borrow> borrows = borrowService.findByBorrower(borrower);
        boolean alreadyStored = borrows.stream()
                .anyMatch(b -> b.getBook().getBookISBN().equals(book.getBookISBN()));

        if (alreadyStored) {
            model.addAttribute("book", book);
            model.addAttribute("duplicateByBorrower", true);
            return "local_books";
        }

        // Fetch book by ISBN if exists in DB
        Book existingBook = bookService.getBooksByISBN(book.getBookISBN());
        if (existingBook != null) {
            String inputTitle = book.getBookTitle().trim().toLowerCase();
            String inputAuthor = book.getBookAuthor().trim().toLowerCase();

            String dbTitle = existingBook.getBookTitle().trim().toLowerCase();
            String dbAuthor = existingBook.getBookAuthor().trim().toLowerCase();

            boolean titleMatches = inputTitle.equals(dbTitle);
            boolean authorMatches = inputAuthor.equals(dbAuthor);

            if (!titleMatches || !authorMatches) {
                model.addAttribute("book", book);
                model.addAttribute("isbnMismatch", true);
                return "local_books";
            }

            // Use existing book
            session.setAttribute("book", existingBook);
        } else {
            // ✅ Save new book and store in session
            bookService.save(book);
            session.setAttribute("book", book);
        }

        return "redirect:/borrower/store/local/details";
    }

    @GetMapping("/store/local/details")
    public String showLocalBookDetails(Model model, HttpSession session) {
        Book book = (Book) session.getAttribute("book");
        if (book == null) {
            return "redirect:/borrower/store/local"; // fallback in case of session expiry
        }

        Borrow borrow = new Borrow();
        borrow.setBook(book); // optional — just for display

        model.addAttribute("borrow", borrow);
        return "localbooks_details";
    }

    @PostMapping("/store/local/details")
    public String saveLocalBookDetails(@ModelAttribute("borrow") Borrow borrow,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) {

        if (borrow.getBook() == null || borrow.getBook().getBookISBN() == null) {
            redirectAttributes.addFlashAttribute("error", "Missing book ISBN");
            return "redirect:/borrower/store/local";
        }

        Book book = bookService.getBooksByISBN(borrow.getBook().getBookISBN());
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid ISBN. Book not found.");
            return "redirect:/borrower/store/local/details";
        }

        // ADD THIS: get currently logged in borrower
        Borrower borrower = borrowerService.findByUsername(principal.getName());
        borrow.setBorrower(borrower);  // SET borrower properly

        borrow.setBook(book);          // ensure book object is real
        borrowService.save(borrow);

        redirectAttributes.addFlashAttribute("success", "Borrow record saved!");
        return "redirect:/borrower/mainpage";
    }

    @PostMapping("/borrow/delete")
    public String deleteBorrow(@RequestParam("borrowId") Long borrowId,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        Borrower borrower = borrowerService.findByUsername(principal.getName());
        Optional<Borrow> optionalBorrow = borrowService.getBorrowById(borrowId);

        if (optionalBorrow.isPresent()) {
            Borrow borrow = optionalBorrow.get();

            // Ensure the borrow record belongs to the current user
            if (borrow.getBorrower().getBorrowerId().equals(borrower.getBorrowerId())) {
                borrowService.deleteBorrow(borrow);
                redirectAttributes.addFlashAttribute("success", "Borrow record deleted.");
            } else {
                redirectAttributes.addFlashAttribute("error", "You are not authorized to delete this record.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Borrow record not found.");
        }

        return "redirect:/borrower/mainpage";
    }

}