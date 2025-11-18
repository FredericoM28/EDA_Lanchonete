/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HP
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Venda;
import model.Fila;
import model.ItemVenda;

public class ListaVendasFrame extends JFrame {
    private JTable tabelaVendas;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;
    private JButton btnVoltar;
    private JLabel lblTotalVendas;
    private JLabel lblValorTotal;
    private DecimalFormat df = new DecimalFormat("#,##0.00 MT");

    public ListaVendasFrame() {
        initComponents();
        carregarVendas();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Sistema Pizzaria - Lista de Vendas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setResizable(true);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Título
        JLabel lblTitle = new JLabel("RELATÓRIO DE VENDAS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(70, 130, 180));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Painel central com tabela
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 240, 240));

        // Modelo da tabela
        String[] colunas = {"ID Venda", "Data", "Itens", "Quantidade Total", "Valor Total", "Valor Recebido", "Troco"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaVendas = new JTable(tableModel);
        tabelaVendas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaVendas.setRowHeight(25);
        tabelaVendas.setSelectionBackground(new Color(70, 130, 180, 100));
        tabelaVendas.setSelectionForeground(Color.BLACK);
        tabelaVendas.setGridColor(new Color(200, 200, 200));

        // Cabeçalho da tabela
        JTableHeader header = tabelaVendas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel de estatísticas
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statsPanel.setBackground(new Color(220, 230, 240));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        lblTotalVendas = new JLabel("Total de Vendas: 0");
        lblValorTotal = new JLabel("Valor Total: 0,00 MT");
        
        lblTotalVendas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblValorTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        lblTotalVendas.setForeground(new Color(70, 130, 180));
        lblValorTotal.setForeground(new Color(70, 130, 180));

        statsPanel.add(lblTotalVendas);
        statsPanel.add(lblValorTotal);

        centerPanel.add(statsPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");

        stylizeButton(btnAtualizar, new Color(70, 130, 180));
        stylizeButton(btnVoltar, new Color(120, 120, 120));

        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnVoltar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarVendas();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(mainPanel);
    }

    private void carregarVendas() {
        // Limpar tabela
        tableModel.setRowCount(0);

        Fila<Venda> filaVendas = Venda.lerVenda();
        if (filaVendas == null || filaVendas.estaVazia()) {
            JOptionPane.showMessageDialog(this, "Nenhuma venda encontrada!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            atualizarEstatisticas(0, 0);
            return;
        }

        // Criar fila temporária para não perder dados
        Fila<Venda> tempFila = new Fila<>();
        int totalVendas = 0;
        double valorTotalGeral = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (!filaVendas.estaVazia()) {
            Venda venda = filaVendas.desenfileirar();
            tempFila.enfileirar(venda);
            
            // Calcular totais da venda
            int qtdTotal = calcularQuantidadeTotal(venda);
            String itens = obterDescricaoItens(venda);
            float troco = venda.getValorRecebido() - venda.getValorTotal();
            
            // Adicionar à tabela
            Object[] row = {
                venda.getIdVenda(),
                venda.getDataDeVenda().format(formatter),
                itens,
                qtdTotal,
                df.format(venda.getValorTotal()),
                df.format(venda.getValorRecebido()),
                df.format(troco)
            };
            tableModel.addRow(row);
            
            totalVendas++;
            valorTotalGeral += venda.getValorTotal();
        }

        // Restaurar fila original
        while (!tempFila.estaVazia()) {
            filaVendas.enfileirar(tempFila.desenfileirar());
        }

        atualizarEstatisticas(totalVendas, valorTotalGeral);
    }

    private int calcularQuantidadeTotal(Venda venda) {
        int total = 0;
        Fila<ItemVenda> itens = venda.getItemVenda();
        Fila<ItemVenda> temp = new Fila<>();
        
        while (!itens.estaVazia()) {
            ItemVenda item = itens.desenfileirar();
            temp.enfileirar(item);
            total += item.getQtd();
        }
        
        // Restaurar fila de itens
        while (!temp.estaVazia()) {
            itens.enfileirar(temp.desenfileirar());
        }
        
        return total;
    }

    private String obterDescricaoItens(Venda venda) {
        StringBuilder sb = new StringBuilder();
        Fila<ItemVenda> itens = venda.getItemVenda();
        Fila<ItemVenda> temp = new Fila<>();
        int count = 0;
        
        while (!itens.estaVazia() && count < 3) { // Mostrar apenas 3 itens
            ItemVenda item = itens.desenfileirar();
            temp.enfileirar(item);
            if (count > 0) sb.append(", ");
            sb.append(item.getItem().getNome()).append("(").append(item.getQtd()).append(")");
            count++;
        }
        
        // Restaurar fila de itens
        while (!temp.estaVazia()) {
            itens.enfileirar(temp.desenfileirar());
        }
        
        if (count >= 3) {
            sb.append("...");
        }
        
        return sb.toString();
    }

    private void atualizarEstatisticas(int totalVendas, double valorTotal) {
        lblTotalVendas.setText("Total de Vendas: " + totalVendas);
        lblValorTotal.setText("Valor Total: " + df.format(valorTotal));
    }

    private void stylizeButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker()),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    public static void main(String[] args) {
        try {
          //  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListaVendasFrame().setVisible(true);
            }
        });
    }
}
