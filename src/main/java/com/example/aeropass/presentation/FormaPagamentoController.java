//package com.example.aeropass.presentation;
//
//
//import com.example.aeropass.application.DTOs.*;
//import com.example.aeropass.application.services.FormaPagamentoService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/forma-pagamento")
//@Tag(name = "Forma de pagamento", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de formas de pagamento do sistema!")
//public class FormaPagamentoController {
//
//    @Autowired
//    private FormaPagamentoService formaPagamentoService;
//
//    @GetMapping("/listar")
//    @Operation(summary = "Método de consulta de formas de pagamento.", description = "Método responsável por efetuar a consulta de todas as formas de pagamento, sem filtro.")
//    public ResponseEntity<List<FormaPagamentoResponse>> listarTodos(){
//
//        return  ResponseEntity.ok(formaPagamentoService.listarTodasFormaPagamentoTable());
//    }
//
//    @PostMapping("/cadastro")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Método de criação de formas de pagamento pelo administrador.", description = "Método responsável por efetuar a criação de formas de pagamento pelo administrador.")
//    public ResponseEntity<CriarFormaPagamentoResponse> criarFormaPagamento(@RequestBody CriarFormaPagamentoRequest criarFormaPagamentoRequest) {
//        try {
//            CriarFormaPagamentoResponse respostaSalvar = formaPagamentoService.criarFormaPagamento(criarFormaPagamentoRequest);
//            return ResponseEntity.ok(respostaSalvar);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//}
