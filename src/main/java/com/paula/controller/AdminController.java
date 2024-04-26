// package com.paula.controller;

// import java.util.ArrayList;

// import javax.servlet.http.HttpSession;
// // import javax.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// // import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RequestParam;
// // import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// // import com.paula.model.Book;
// import com.paula.model.Role;
// import com.paula.model.User;
// // import com.paula.services.BookService;
// // import com.paula.services.ExchangeService;
// import com.paula.services.UserService;
// import com.paula.util.Encriptation;

// @Controller
// @RequestMapping("/admin")
// public class AdminController {

//     @Autowired
//     private UserService userService;

//     // @Autowired
//     // private BookService bookService;

//     // @Autowired
//     // private ExchangeService exchangeService;

//     @GetMapping("/login")
//     public String loginPage(Model model) {
//         model.addAttribute("user", new User());
//         return "/common/login";
//     }

//     @PostMapping("/login")
//     public String loginAdmin(@ModelAttribute User user, HttpSession hSession) {
//         if (user.getEmail() != null && user.getPassword() != null) {
//             for (User userFind : userService.getUsers()) {
//                 if (userFind.getEmail().equals(user.getEmail())
//                         && Encriptation.validatePassword(user.getPassword(), userFind.getPassword()) == true
//                         && (userFind.getRole().equals(Role.admin))) {
//                     hSession.setAttribute("adminUser", user.getEmail());
//                     if (userFind.getRole().equals(Role.admin)) {
//                         hSession.setAttribute("userRole", Role.admin.toString());
//                     }
//                     return "admin";
//                 }
//             }
//         }

//         return "/common/login";
//     }

//     @GetMapping("/logOut")
//     public String logOut(HttpSession hSession) {
//         if (hSession.getAttribute("adminUser") != null) {
//             hSession.setAttribute("adminUser", null);
//             hSession.setAttribute("userRole", null);
//             return "/admin/login";
//         }

//         return "/admin";
//     }

//     @GetMapping("/users")
//     public String getUsers(Model model, HttpSession hSession) {
//         if (hSession.getAttribute("adminUser") != null) {
//             ArrayList<User> registeredUsers = new ArrayList<User>();
//             Role role = Role.registeredUser;
//             registeredUsers = userService.getUsersRoles(role);
//             hSession.setAttribute("adminRegistered", registeredUsers);
//             return "admin/adminRegisteredUsers";
//         }
//         return "/admin/login";
//     }

//     @GetMapping("/users/removeRegisteredUser/{id}")
//     public String removeRegisteredUser(HttpSession hSession, @PathVariable(name = "userId", required = false) int id) {
//         if (hSession.getAttribute("adminUser") != null) {
//             User registeredUser = userService.getUserById(id);
//             userService.removeUser(registeredUser);
//             return "/admin/clientes";
//         }

//         return "admin/adminRegisteredUsers";
//     }

//     // @GetMapping("/procesarPedidos")
//     // public String cargaPedidos(Model model, HttpSession hSession) {
//     // if (hSession.getAttribute("userAdmin") != null) {
//     // ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
//     // pedidos = pes.getPedidos();
//     // hSession.setAttribute("pedidosAdmin", pedidos);
//     // return "administrador/administracionProcesarPedidos";
//     // }

//     // return "redirect:/admin/login";
//     // }

//     // @GetMapping("/procesarPedidos/enviarPedido/{id}")
//     // public String enviarPedido(RedirectAttributes ra, HttpSession hSession,
//     // @PathVariable(name = "id") int id) {
//     // if (hSession.getAttribute("userAdmin") != null) {
//     // Pedido pedidoSelecionado = pes.obtenerPedidouser(id);
//     // if (pedidoSelecionado.getEstado_pedido() != Estado.PC &&
//     // pedidoSelecionado.getEstado_pedido() != Estado.C
//     // && pedidoSelecionado.getEstado_pedido() != Estado.E) {
//     // Estado nuevoEstado = Estado.E;
//     // pes.cambiarEstado(nuevoEstado, id);

//     // user user = us.buscaruser(hSession.getAttribute("userAdmin").toString());
//     // Pedido pedido = pes.obtenerPedidouser(id);
//     // ArrayList<LineaPedido> productosPedido = new ArrayList<LineaPedido>();
//     // productosPedido = lps.verLineasPedido(pedido);
//     // PDFFactura.crearFactura(user, pedido, productosPedido);
//     // return "redirect:/admin/procesarPedidos";
//     // } else {
//     // String numPedido = pedidoSelecionado.getNum_factura();
//     // if (pedidoSelecionado.getEstado_pedido() == Estado.PC) {
//     // ra.addFlashAttribute("errorEnviarPedidoAdmin",
//     // "No se puede enviar el pedido " + numPedido + " por que esta pendiente de
//     // cancelacion.");
//     // }
//     // if (pedidoSelecionado.getEstado_pedido() == Estado.C) {
//     // ra.addFlashAttribute("errorEnviarPedidoAdmin",
//     // "No se puede enviar el pedido " + numPedido + " por que ha sido cancelado.");
//     // }
//     // if (pedidoSelecionado.getEstado_pedido() == Estado.E) {
//     // ra.addFlashAttribute("errorEnviarPedidoAdmin",
//     // "No se puede enviar el pedido " + numPedido + " por que ya ha sido
//     // enviado.");
//     // }
//     // return "redirect:/admin/procesarPedidos";
//     // }
//     // }
//     // return "redirect:/admin";
//     // }

//     // @GetMapping("/procesarPedidos/cancelarPedido/{id}")
//     // public String cancelarPedido(RedirectAttributes ra, HttpSession hSession,
//     // @PathVariable(name = "id") int id) {
//     // if (hSession.getAttribute("userAdmin") != null) {
//     // Pedido pedidoSelecionado = pes.obtenerPedidouser(id);
//     // if (pedidoSelecionado.getEstado_pedido() != Estado.E &&
//     // pedidoSelecionado.getEstado_pedido() != Estado.C) {
//     // Estado nuevoEstado = Estado.C;
//     // pes.cambiarEstado(nuevoEstado, id);
//     // return "redirect:/admin/procesarPedidos";
//     // } else {
//     // String numPedido = pedidoSelecionado.getNum_factura();
//     // if (pedidoSelecionado.getEstado_pedido() == Estado.E) {
//     // ra.addFlashAttribute("errorCancelarPedidoAdmin",
//     // "No se puede cancelar el pedido " + numPedido + " por que ya ha sido
//     // enviado.");
//     // }
//     // if (pedidoSelecionado.getEstado_pedido() == Estado.C) {
//     // ra.addFlashAttribute("errorCancelarPedidoAdmin",
//     // "No se puede cancelar el pedido " + numPedido + " por que ya ha sido
//     // cancelado.");
//     // }
//     // return "redirect:/admin/procesarPedidos";
//     // }
//     // }
//     // return "redirect:/admin";
//     // }
// }