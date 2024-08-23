/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ex3_conta_bancaria_socket_tcp;

/**
 *
 * @author Andre
 */
/*3)  Implemente um servidor TCP responsável pelo gerenciamento de uma conta bancária, esta contendo um saldo e oferecendo serviços 
responsáveis pela manipulação da conta (saque, depósito e consulta do saldo). Desenvolva também a aplicação cliente, contendo 
um menu que possibilite ao usuário escolher a operação desejada.*/

import java.io.*;
import java.net.*;

public class Servidor {
    private static double saldo = 1000.0; // Saldo inicial

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor aguardando conexoes...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Configuração para leitura e escrita nos sockets
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                // Define timeout para operações de leitura
                //clientSocket.setSoTimeout(10000); // Timeout de 5000 milissegundos (5 segundos)

                // L?? a operação do cliente
                String operacao = dis.readUTF();
                
                // Executa a operações com base no tipo
                String resposta = "";
                switch (operacao) {
                    case "SAQUE":
                        double valorSaque = dis.readDouble();
                        resposta = executarSaque(valorSaque);
                        break;
                    case "DEPOSITO":
                        double valorDeposito = dis.readDouble();
                        resposta = executarDeposito(valorDeposito);
                        break;
                    case "CONSULTA":
                        resposta = "Saldo atual: " + saldo;
                        break;
                    default:
                        resposta = "operações invalida.";
                }

                // Envia a resposta de volta para o cliente
                dos.writeUTF(resposta);

                // Fecha a conexão com o cliente
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String executarSaque(double valorSaque) {
        if (saldo >= valorSaque) {
            saldo -= valorSaque;
            return "Saque de " + valorSaque + " realizado. Novo saldo: " + saldo;
        } else {
            return "Saldo insuficiente para saque.";
        }
    }

    private static String executarDeposito(double valorDeposito) {
        saldo += valorDeposito;
        return "Deposito de " + valorDeposito + " realizado. Novo saldo: " + saldo;
    }
}
