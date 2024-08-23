/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ex3_conta_bancaria_socket_tcp;

import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author Andre
 */
/*3)  Implemente um servidor TCP responsável pelo gerenciamento de uma conta bancária, esta contendo um saldo e 
oferecendo serviços responsáveis pela manipulação da conta (saque, depósito e consulta do saldo). Desenvolva também 
a aplicação cliente, contendo um menu que possibilite ao usuario escolher a operação desejada. */

public class Cliente {
  public static void main(String[] args) {
        try {
            // Conecta ao servidor
            Socket socket = new Socket("localhost", 12345);

            // Configuração para leitura e escrita nos sockets
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Define timeout para operações de escrita
            //socket.setSoTimeout(5000); // Timeout de 5000 milissegundos (5 segundos)

            // Menu de operações
            System.out.println("Escolha uma operacao:");
            System.out.println("1. Saque");
            System.out.println("2. Deposito");
            System.out.println("3. Consulta de Saldo");
            System.out.println("DIGITE A OPCAO: ");

            // Recebe a escolha do usuário
            Scanner scanner = new Scanner(System.in);
            int opcao = Integer.parseInt(scanner.nextLine());

            // Envia a operação para o servidor
            enviarOperacao(dos, opcao);
            /*a função enviarOperacao é responsável por enviar a operação (saque, depósito ou consulta) para o servidor, 
            e essa informação é necessária independentemente da escolha do usuário em realizar operações adicionais que 
            exigem entrada de valor, por isso ela vem antes das opções 1 e 2, caso contrário daria erro.*/
            
            // Executa operações que exigem entrada de valor
            if (opcao == 1 || opcao == 2) {
                System.out.print("Digite o valor: ");
                double valor = Double.parseDouble(scanner.nextLine());
                dos.writeDouble(valor);
            }
            

            // Recebe e exibe a resposta do servidor
            String resposta = dis.readUTF();
            System.out.println("Resposta do servidor: " + resposta);

            // Fecha a conexão com o servidor
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void enviarOperacao(DataOutputStream dos, int opcao) throws IOException {
        switch (opcao) {
            case 1:
                dos.writeUTF("SAQUE");
                break;
            case 2:
                dos.writeUTF("DEPOSITO");
                break;
            case 3:
                dos.writeUTF("CONSULTA");
                break;
            default:
                System.out.println("Operacao invalida.");
        }
    }
}
