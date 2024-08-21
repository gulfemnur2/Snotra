package com.example.controllers;

import com.example.models.*;
import com.example.services.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/orders")
public class OrderController {
    private final BooksServiceForRepository service;
    private final UsersServiceForRepository userService;
    private final CommentsServiceForRepository commentService;
    private final AdminsServiceForRepository adminsService;
    private final CommentsRepository commentsRepository;
    private final FileSystemStorageService storageService;

    @Autowired
    public OrderController(BooksServiceForRepository service,
                           UsersServiceForRepository userService,
                           CommentsServiceForRepository commentService,
                           AdminsServiceForRepository adminsService,
                           CommentsRepository commentsRepository, FileSystemStorageService storageService) {
        this.service = service;
        this.userService = userService;
        this.commentService = commentService;
        this.adminsService = adminsService;
        this.commentsRepository = commentsRepository;
        this.storageService = storageService;
    }

    @GetMapping("/public/")
    public String showAllOrders(Model model) {
        List<BookModel> orders = service.getOrders();
        model.addAttribute("kitaplar", orders);
        return "main";
    }

    @GetMapping("/form")
    public String showNewForm(Model model) {
        model.addAttribute("order", new BookModel());
        model.addAttribute("files", new ArrayList<CoverModel>());
        return "addNewBookForm";
    }

    @PostMapping("/admin/addNew")
    public String addNew(@Valid BookModel newOrder,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "addNewBookForm";
        }

        newOrder.setId(null);
        service.addOne(newOrder);

        redirectAttributes.addAttribute("bookId", newOrder.getId());
        return "uploadPage";
    }

    @GetMapping("/admin/uploadPage")
    public String uploadPage(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "uploadPage";
    }

    @PostMapping("/admin/uploadCover")
    public String uploadCover(@RequestParam("coverFile") MultipartFile coverFile,
                              @RequestParam("bookId") Long bookId,
                              RedirectAttributes redirectAttributes) {
        if (coverFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kapak resmi seçilmedi!");
            return "redirect:/admin/uploadPage";
        }

        String filePath = storageService.store(coverFile);

        BookModel book = service.getById(bookId);
        if (book != null) {
            book.setKapak(filePath);
            service.updateOne(bookId, book);
            redirectAttributes.addFlashAttribute("message", "Kapak resmi başarıyla yüklendi!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Kitap bulunamadı!");
        }

        return "redirect:/orders/admin/";
    }

    @GetMapping("/admin/")
    public String showAdminPage(Model model) {
        List<BookModel> orders = service.getOrders();
        model.addAttribute("kitaplar", orders);
        return "admin";
    }

    @GetMapping("/admin/editForm/{id}")
    public String displayEditForm(@PathVariable("id") Long id, Model model) {
        BookModel order = service.getById(id);
        model.addAttribute("orderModel", order);
        return "editForm";
    }

    @PostMapping("/admin/doUpdate")
    public String updateBook(@Valid BookModel order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editForm";
        }

        service.updateOne(order.getId(), order);

        return "redirect:/orders/admin/updateCover?bookId=" + order.getId();
    }

    @GetMapping("/admin/updateCover")
    public String updateCoverPage(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "updateCover";
    }

    @PostMapping("/admin/updateCover")
    public String updateCover(@RequestParam("coverFile") MultipartFile coverFile,
                              @RequestParam("bookId") Long bookId,
                              RedirectAttributes redirectAttributes) {
        if (coverFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kapak resmi seçilmedi!");
            return "redirect:/orders/admin/updateCover";
        }

        String filePath = storageService.store(coverFile);

        BookModel book = service.getById(bookId);
        if (book != null) {
            book.setKapak(filePath);
            service.updateOne(bookId, book);
            redirectAttributes.addFlashAttribute("message", "Kapak resmi başarıyla yüklendi!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Kitap bulunamadı!");
        }

        return "redirect:/orders/admin/";
    }

    @PostMapping("/admin/delete/")
    public String deleteOrder(@Valid @ModelAttribute("orderModel") BookModel order,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        service.deleteOne(order.getId());
        redirectAttributes.addFlashAttribute("message", "Kitap başarıyla silindi!");
        return "redirect:/orders/admin/";
    }

    @PostMapping("/public/book/{id}")
    public String bookDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
        BookModel kitap = service.getById(id);

        model.addAttribute("kitap", kitap);

        UserModel currentUser = (UserModel) session.getAttribute("user");

        if (currentUser != null) {
            CommentEntity kullaniciYorumu = commentService.getYorumByKullaniciIdAndKitapId(currentUser.getId(), id);
            model.addAttribute("kullaniciYorumu", kullaniciYorumu);
        }

        List<CommentEntity> tumYorumlar = commentService.getYorumlarByKitapId(id);
        model.addAttribute("tumYorumlar", tumYorumlar);

        return "book";
    }

    @PostMapping("/public/search")
    public String search(@Valid @ModelAttribute("searchModel") SearchModel searchModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        String searchTerm = searchModel.getSearchTerm();
        List<BookModel> orders = service.searchOrders(searchTerm);
        model.addAttribute("kitaplar", orders);
        return "main";
    }

    @GetMapping("/public/users")
    public String showAllUsers(Model model) {
        List<UserModel> users = userService.getUsers();
        model.addAttribute("kullanici", users);
        return "users";
    }

    @GetMapping("/public/userForm")
    public String showUserForm(Model model) {
        model.addAttribute("kullanici", new UserModel());
        return "register";
    }

    @PostMapping("/public/register")
    public String register(@Valid UserModel newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        newUser.setId(null);
        userService.addUser(newUser);
        redirectAttributes.addFlashAttribute("message", "Kullanıcı başarıyla eklendi!");
        return "redirect:/orders/public/";
    }

    @GetMapping("/public/loginForm")
    public String showLoginForm(Model model) {
        model.addAttribute("kullanici", new UserModel());
        model.addAttribute("admin", new AdminModel());
        return "login";
    }

    @PostMapping("/public/login")
    public String login(@Valid UserModel newUser, @Valid AdminModel newAdmin, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            UserModel authenticatedUser = userService.authenticateUser(newUser.getEmail(), newUser.getSifre());
            if (authenticatedUser != null) {
                session.setAttribute("user", authenticatedUser);
                return "redirect:/orders/public/";
            }

            AdminModel authenticatedAdmin = adminsService.authenticateAdmin(newAdmin.getAdminEmail(), newAdmin.getAdminSifre());
            if (authenticatedAdmin != null) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                authenticatedAdmin.getAdminEmail(),
                                null,
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
                        )
                );
                session.setAttribute("admin", authenticatedAdmin);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                return "redirect:/orders/admin/";
            }

            model.addAttribute("errorMessage", "Geçersiz email veya şifre.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Giriş sırasında bir hata oluştu: " + e.getMessage());
        }

        model.addAttribute("kullanici", newUser);
        model.addAttribute("admin", newAdmin);
        return "login";
    }

    @GetMapping("/public/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model, HttpSession session) {
        UserModel currentUser = (UserModel) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/orders/public/loginForm";
        }

        UserModel kullanici = userService.findById(id);
        model.addAttribute("kullanici", kullanici);

        if (kullanici.getOduncId() != null) {
            BookModel kitap = service.getById(kullanici.getOduncId());
            if (kitap != null) {
                boolean oduncSuresiGecti = service.isOduncSuresiGecti(kitap);
                model.addAttribute("oduncSuresiGecti", oduncSuresiGecti);

                if (oduncSuresiGecti) {
                    model.addAttribute("message", "Ödünç süresi doldu, kitap en kısa sürede iade ediniz.");
                    model.addAttribute("messageType", "info");
                } else {
                    LocalDateTime oduncAlmaTarihi = kitap.getOduncAlmaTarihi();
                    long kalanGun = ChronoUnit.DAYS.between(LocalDate.now(), oduncAlmaTarihi.plusDays(1));
                    model.addAttribute("kalanGun", kalanGun);
                }
            }
        }

        return "profile";
    }

    @GetMapping("/public/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/orders/public/loginForm";
    }

    @PostMapping("/public/updateUser")
    public String updateUser(@Valid @ModelAttribute("kullanici") UserModel user,
                             BindingResult bindingResult,
                             @RequestParam("mevcutSifre") String mevcutSifre,
                             @RequestParam("sifre") String sifre,
                             Model model) {

        if (!userService.check(user, mevcutSifre)) {
            model.addAttribute("messageType", "error");
            model.addAttribute("message", "Mevcut şifre yanlış.");
            return "editUser";
        }

        if (userService.check(user, sifre)) {
            if (mevcutSifre.equals(sifre)) {
                model.addAttribute("messageType", "error");
                model.addAttribute("message", "Yeni şifre mevcut şifreyle aynı olamaz.");
                return "editUser";
            }
        }

        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        userService.update(user.getId(), user);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Kullanıcı bilgileri başarıyla güncellendi.");
        return "redirect:/orders/public/profile/" + user.getId();
    }

    @GetMapping("/public/editUser/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        UserModel user = userService.findById(id);
        model.addAttribute("kullanici", user);
        return "editUser";
    }

    @PostMapping("/public/deleteUser/")
    public String deleteUser(@Valid UserModel user, BindingResult bindingResult, Model model, HttpSession session) {
        userService.deleteById(user.getId());
        List<UserModel> users = userService.getUsers();
        model.addAttribute("kullanicilar", users);
        model.addAttribute("searchModel", new SearchModel());

        session.invalidate();

        return "redirect:/orders/public/loginForm";
    }

    @GetMapping("/public/showSearchUser")
    public String showSearchUser(Model model) {
        model.addAttribute("searchModel", new SearchModel());
        return "searchUser";
    }

    @PostMapping("/public/searchUser")
    public String searchUser(@Valid SearchModel searchModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "searchUser";
        }
        String searchTerm = searchModel.getSearchTerm();
        List<UserModel> users = userService.searchUser(searchTerm);
        model.addAttribute("kullanici", users);
        return "users";
    }

    @GetMapping("/public/sort")
    public String sortBooks(@RequestParam String sortField, @RequestParam String sortDirection, Model model) {
        boolean ascending = "asc".equalsIgnoreCase(sortDirection);
        List<BookModel> sortedBooks = service.sortOrders(sortField, ascending);
        model.addAttribute("kitaplar", sortedBooks);
        return "main";
    }

    @GetMapping("/public/borrow/{id}")
    public String borrow(@PathVariable Long id, Model model, HttpSession session) {
        UserModel currentUser = (UserModel) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/orders/public/loginForm";
        }
        if (currentUser.getOduncKitap() != null && !currentUser.getOduncKitap().isEmpty()) {
            model.addAttribute("message", "Zaten ödünç alınmış bir kitabınız var. Yeni kitap ödünç alamazsınız.");
            model.addAttribute("messageType", "error");
            BookModel kitap = service.getById(id);
            model.addAttribute("kitap", kitap);
            return "book";
        }
        BookModel kitap = service.getById(id);
        if (kitap == null) {
            model.addAttribute("message", "Kitap bulunamadı.");
            model.addAttribute("messageType", "error");
            return "book";
        }
        if (!kitap.isOdunc()) {
            kitap.setOdunc(true);
            kitap.setOduncAlanKullanici(currentUser.getKullaniciAdi());
            kitap.setOduncAlmaTarihi(LocalDateTime.now());
            service.updateOne(kitap.getId(), kitap);

            currentUser.setOduncKitap(kitap.getBaslik());
            currentUser.setOduncId(kitap.getId());
            currentUser.setOduncAlmaTarihi(LocalDateTime.now());
            userService.update(currentUser.getId(), currentUser);

            model.addAttribute("message", "Kitap başarıyla ödünç alındı.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Bu kitap ödünç alınamaz.");
            model.addAttribute("messageType", "error");
        }
        model.addAttribute("kitap", kitap);
        return "book";
    }

    @GetMapping("/public/return/{id}")
    public String returnBook(@PathVariable Long id, Model model, HttpSession session) {
        UserModel currentUser = (UserModel) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/orders/public/loginForm";
        }
        if (id == null) {
            model.addAttribute("message", "Geçersiz kitap ID.");
            model.addAttribute("messageType", "error");
            return "profile";
        }
        try {
            BookModel kitap = service.getById(id);
            if (kitap == null) {
                model.addAttribute("message", "Kitap bulunamadı.");
                model.addAttribute("messageType", "error");
                return "profile";
            }
            if (kitap.isOdunc()) {
                kitap.setOdunc(false);
                kitap.setOduncAlanKullanici(null);
                kitap.setOduncAlmaTarihi(null);
                service.updateOne(kitap.getId(), kitap);

                currentUser.setOduncKitap(null);
                currentUser.setOduncId(null);
                currentUser.setOduncAlmaTarihi(null);
                userService.update(currentUser.getId(), currentUser);

                model.addAttribute("message", "Kitap başarıyla teslim edildi.");
                model.addAttribute("messageType", "success");
            } else {
                model.addAttribute("message", "Bu kitap teslim edilemez.");
                model.addAttribute("messageType", "error");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Bir hata oluştu: " + e.getMessage());
            model.addAttribute("messageType", "error");
        }

        model.addAttribute("kullanici", currentUser);
        return "profile";
    }

    @GetMapping("/public/showProfile/{id}")
    public String showProfile1(@PathVariable Long id, Model model) {
        UserModel kullanici = userService.findById(id);
        if (kullanici == null) {
            System.out.println("Kullanıcı bulunamadı. ID: " + id);
        } else {
            System.out.println("Kullanıcı bulundu: " + kullanici.toString());
        }
        model.addAttribute("kullanici", kullanici);
        return "showProfile";
    }

    @GetMapping("/public/book/{kitapId}/comments")
    public String showComments(@PathVariable Long kitapId, Model model, HttpSession session) {
        List<CommentEntity> yorumlar = commentsRepository.findByKitapId(kitapId);
        List<CommentModel> commentModels = new ArrayList<>();

        for (CommentEntity yorum : yorumlar) {
            UserModel yazar = userService.findById(yorum.getKullaniciId());
            if (yazar != null) {
                CommentModel commentModel = new CommentModel();
                commentModel.setYorum(yorum.getYorum());
                commentModel.setPuan(yorum.getPuan());
                commentModel.setYorumTarihi(yorum.getYorumTarihi());
                commentModel.setYazarAd(yazar.getAd());
                commentModel.setYazarSoyad(yazar.getSoyad());
                commentModels.add(commentModel);
            }
        }

        BookModel kitap = service.getById(kitapId);

        model.addAttribute("tumYorumlar", commentModels);
        model.addAttribute("kitapId", kitapId);
        model.addAttribute("kitap", kitap);
        return "comments";
    }

    @PostMapping("/public/comment")
    public String addComment(@ModelAttribute CommentEntity yeniYorum, @RequestParam Long kitapId, HttpSession session, Model model) {
        UserModel currentUser = (UserModel) session.getAttribute("user");
        if (currentUser == null) {
            model.addAttribute("message", "Yorum yapabilmek için giriş yapmalısınız.");
            model.addAttribute("messageType", "error");
            return "redirect:/orders/public/loginForm";
        }

        yeniYorum.setKullaniciId(currentUser.getId());
        yeniYorum.setKitapId(kitapId);
        yeniYorum.setYorumTarihi(new Date());

        long yeniYorumId = commentService.ekle(yeniYorum);

        if (yeniYorumId > 0) {
            commentService.updateKitapPuanOrtalama(kitapId);
            model.addAttribute("message", "Yorum başarıyla eklendi.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Yorum eklenirken bir hata oluştu.");
            model.addAttribute("messageType", "error");
        }

        return "redirect:/orders/public/book/" + kitapId + "/comments";
    }

    @GetMapping("/public/comment/edit/{id}")
    public String editComment(@PathVariable("id") Long id, Model model) {
        CommentEntity yorum = commentService.getById(id);
        BookModel kitap = service.getById(yorum.getKitapId());
        model.addAttribute("yorum", yorum);
        model.addAttribute("kitap", kitap);
        return "editComment";
    }

    @PostMapping("/public/comment/update")
    public String updateComment(@Valid CommentEntity yorum, BindingResult bindingResult, Model model) {
        Long kitapId = yorum.getKitapId();

        if (bindingResult.hasErrors()) {
            BookModel kitap = service.getById(yorum.getKitapId());
            model.addAttribute("yorum", yorum);
            model.addAttribute("kitap", kitap);
            return "editComment";
        }

        commentService.guncelle(yorum.getId(), yorum);
        commentService.updateKitapPuanOrtalama(kitapId);

        List<CommentEntity> yorumlar = commentService.getYorumlar();
        model.addAttribute("yorumlar", yorumlar);
        model.addAttribute("searchModel", new SearchModel());
        return "redirect:/orders/public/book/" + kitapId + "/comments";
    }

    @PostMapping("/public/comment/delete")
    public String deleteComment(@Valid CommentModel yorum, BindingResult bindingResult, Model model) {
        commentService.sil(yorum.getId());

        Long kitapId = yorum.getKitapId();
        commentService.updateKitapPuanOrtalama(kitapId);

        List<CommentEntity> tumYorumlar = commentService.getYorumlarByKitapId(kitapId);
        model.addAttribute("tumYorumlar", tumYorumlar);
        model.addAttribute("searchModel", new SearchModel());

        return "redirect:/orders/public/book/" + kitapId + "/comments";
    }

    @GetMapping("/public/filter")
    public String filter(@RequestParam("kategori") String kategori, Model model) {
        List<BookModel> filteredOrders = service.getByKategori(kategori);
        model.addAttribute("kitaplar", filteredOrders);
        return "main";
    }

    @GetMapping("/public/filterPage")
    public String filterPage(@RequestParam(value = "minPage", required = false) Integer minPage,
                           @RequestParam(value = "maxPage", required = false) Integer maxPage,
                           Model model) {

        if (minPage == null && maxPage == null) {
            List<BookModel> tumKitaplar = service.getOrders();
            model.addAttribute("kitaplar", tumKitaplar);
            return "main";
        }

        if (minPage != null && maxPage == null) {
            List<BookModel> filtrelenmisKitaplar = service.getByMinSayfa(minPage);
            model.addAttribute("kitaplar", filtrelenmisKitaplar);
            return "main";
        }

        if (minPage == null && maxPage != null) {
            List<BookModel> filtrelenmisKitaplar = service.getByMaxSayfa(maxPage);
            model.addAttribute("kitaplar", filtrelenmisKitaplar);
            return "main";
        }

        if (minPage > maxPage) {
            model.addAttribute("error", "Üst limit, alt limitten küçük olamaz.");
            return "main";
        }

        List<BookModel> filtrelenmisKitaplar = service.getBySayfa(minPage, maxPage);
        model.addAttribute("kitaplar", filtrelenmisKitaplar);
        return "main";
    }
}