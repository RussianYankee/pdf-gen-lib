package com.yankee.invoicegen;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;

public class InvoiceGenerator {
    public void generateInvoice(String filePath, String headerText, String[][] items, double taxRate) {
        Document document = new Document(PageSize.LETTER);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add header
//            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
//            Paragraph header = new Paragraph(headerText, headerFont);
//            document.add(header);
            String logoPath = "src/main/resources/RAMZOR.png";
            String companyName = "Ramzor Appliance Repair";
            String invoiceNumber = "123456";
            String serviceDate = "2021-01-01";
            String dueDate = "2021-01-31";
            String amountDue = "$100.00";
            String customerName = "John Doe";
            String billingAddress = "123 Main St, Anytown, USA";
            String serviceAddress = "456 Elm St, Anytown, USA";



            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
//            headerTable.setWidths(new int[]{1, 2});

            // Left side: company logo and name
            PdfPTable logoTable = new PdfPTable(1);
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(50, 50);
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoTable.addCell(logoCell);

            PdfPCell companyNameCell = new PdfPCell(new Phrase(companyName, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            companyNameCell.setBorder(Rectangle.NO_BORDER);
            companyNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            logoTable.addCell(companyNameCell);

            PdfPCell leftSideCell = new PdfPCell(logoTable);
            leftSideCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(leftSideCell);

            // Right side: invoice details
            PdfPTable detailsTable = new PdfPTable(1);
            detailsTable.setWidthPercentage(100);

            detailsTable.addCell(createDetailCell("Invoice#: " + invoiceNumber));
            detailsTable.addCell(createDetailCell("Service Date: " + serviceDate));
            detailsTable.addCell(createDetailCell("Due Date: " + dueDate));
            detailsTable.addCell(createDetailCell("Amount Due: " + amountDue));

            PdfPCell detailsCell = new PdfPCell(detailsTable);
            detailsCell.setBorder(Rectangle.NO_BORDER);
            detailsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headerTable.addCell(detailsCell);

            document.add(headerTable);

            // Add customer details section
            PdfPTable customerTable = new PdfPTable(1);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10f);
            customerTable.setSpacingAfter(10f);

            customerTable.addCell(createDetailCell("Customer Name: " + customerName));
            customerTable.addCell(createDetailCell("Billing Address: " + billingAddress));
            customerTable.addCell(createDetailCell("Service Address: " + serviceAddress));

            document.add(customerTable);

            // Add table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            PdfPCell cell;

            // Header cells with light gray background
            cell = new PdfPCell(new Phrase("Item"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit Price"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Amount"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            double subtotal = 0;
            for (String[] item : items) {
                String itemName = item[0];
                int quantity = Integer.parseInt(item[1]);
                double unitPrice = Double.parseDouble(item[2]);
                double amount = quantity * unitPrice;

                // Item cells with only bottom border
                cell = new PdfPCell(new Phrase(itemName));
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(quantity)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.format("%.2f", unitPrice)));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.format("%.2f", amount)));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);

                subtotal += amount;
            }

            double tax = subtotal * taxRate;
            double total = subtotal + tax;

            // Add table footer
            composeTableFooter(table, subtotal, tax, total);

            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private PdfPCell createDetailCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 10)));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell createItemCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBorder(Rectangle.BOTTOM);
        return cell;
    }

    private PdfPCell createCell(String text, int alignment, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(border);
        return cell;
    }

    private void composeTableFooter(PdfPTable table, double subtotal, double tax, double total) {
        String[] footerLabels = {"Subtotal", "Tax", "Total"};
        double[] footerValues = {subtotal, tax, total};

        for (int i = 0; i < footerLabels.length; i++) {
            table.addCell(createCell("", Element.ALIGN_LEFT, Rectangle.NO_BORDER));
            table.addCell(createCell("", Element.ALIGN_LEFT, Rectangle.NO_BORDER));
            table.addCell(createCell(footerLabels[i], Element.ALIGN_LEFT, Rectangle.NO_BORDER));
            table.addCell(createCell(String.format("%.2f", footerValues[i]), Element.ALIGN_RIGHT, Rectangle.NO_BORDER));
        }
    }
}
