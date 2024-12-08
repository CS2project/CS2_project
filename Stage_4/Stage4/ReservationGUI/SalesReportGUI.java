import backend.Order;
import backend.SalesReport;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesReportGUI extends JFrame {

    private SalesReport salesReport; // Reference to the backend sales report

    public SalesReportGUI(List<Order> completedOrders) {
        this.salesReport = new SalesReport(completedOrders);

        // Set up the frame
        setTitle("Sales Report");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Report area
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button
        JButton generateReportButton = new JButton("Generate Report");
        add(generateReportButton, BorderLayout.SOUTH);

        // Button action
        generateReportButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder();
            report.append("Total Sales:\n");
            salesReport.generateTotalSales(); // Backend method
            report.append("\nDetailed Item Sales Report:\n");
            salesReport.generateItemSalesReport(); // Backend method
            reportArea.setText(report.toString());
        });
    }
}
