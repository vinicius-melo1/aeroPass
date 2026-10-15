package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FormaPagamentoController {

    @FXML
    private TextField txtTipoPagamento;

    @FXML
    public void salvarFormaPagamento(ActionEvent event) throws IOException {
        // Validar Dados
        String jsonRequest = String.format("{\n" +
                "  \"tipoPagamento\": \"%s\",\n" +
                "  \"secretKey\": \"%s\"\n" +
                "}", txtTipoPagamento.getText(), "f9e08ur903werwi9di90za");

        int respostaAPI = executaMetodoAPI("http://localhost:8080/forma-pagamento/cadastro", jsonRequest, "POST");

        if(respostaAPI == 200) {
            showMessage(Alert.AlertType.INFORMATION, "Forma de pagamento salva com sucesso");
            voltarAoMenu(event);
        } else {
            showMessage(Alert.AlertType.ERROR, "Erro ao salvar forma pagamento");
        }
    }

    @FXML
    public void voltarAoMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    private int executaMetodoAPI(String url, String json, String protocoloHttp) throws IOException {
        URL urlAPI = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlAPI.openConnection();
        connection.setRequestMethod(protocoloHttp);
        connection.setDoOutput(true);

        if(!json.isEmpty()){
            connection.setRequestProperty("Content-Type","application/json");

            try (OutputStream os = connection.getOutputStream()){
                os.write(json.getBytes());
            }
        }
        return connection.getResponseCode();


    }

    private void showMessage(Alert.AlertType type, String msg){
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensagem do sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}
