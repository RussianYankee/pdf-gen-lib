package com.yankee.invoicegen;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.yankee.invoicegen.model.InvoiceHeaderData;
import com.yankee.invoicegen.model.InvoiceItem;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class InvoiceGenerator {
    public void generateInvoice(String filePath, InvoiceHeaderData headerData, InvoiceItem[] items, double taxRate) {
        Document document = new Document(PageSize.LETTER);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add header
//            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
//            Paragraph header = new Paragraph(headerText, headerFont);
//            document.add(header);

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
//            headerTable.setWidths(new int[]{1, 2});
            composeTableHeader(headerTable, headerData);

            document.add(headerTable);

            // Add customer details section
            PdfPTable customerTable = new PdfPTable(1);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10f);
            customerTable.setSpacingAfter(10f);

            customerTable.addCell(createDetailCell("Customer Name: " + headerData.getCustomerName()));
            customerTable.addCell(createDetailCell("Billing Address: " + headerData.getBillingAddress()));
            customerTable.addCell(createDetailCell("Service Address: " + headerData.getServiceAddress()));

            document.add(customerTable);

            // Add table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            PdfPCell cell;

            // Header cells with light gray background
            addItemHeaderCell(table, "Item");
            addItemHeaderCell(table, "Quantity");
            addItemHeaderCell(table, "Unit Price");
            addItemHeaderCell(table, "Amount");

            double subtotal = 0;
            for (InvoiceItem item : items) {
                // Item cells with only bottom border
                cell = createItemCell(item.getName());
                table.addCell(cell);

                cell = createItemCell(String.valueOf(item.getQuantity()), Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = createItemCell(String.format("%.2f", item.getUnitPrice()), Element.ALIGN_RIGHT);
                table.addCell(cell);

                cell = createItemCell(String.format("%.2f", item.getAmount()), Element.ALIGN_RIGHT);
                table.addCell(cell);

                subtotal += item.getAmount();
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

    private PdfPCell createItemCell(String text, int alignment) {
        PdfPCell cell = createItemCell(text);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private void addItemHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
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

    private void composeTableHeader(PdfPTable table, InvoiceHeaderData headerData) throws IOException {
        table.setWidthPercentage(100);

        // Left side: company logo and name
        PdfPTable logoTable = new PdfPTable(1);
        Image logo = Image.getInstance(headerData.getLogoPath());
        logo.scaleToFit(50, 50);
        PdfPCell logoCell = new PdfPCell(logo);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoTable.addCell(logoCell);

        PdfPCell companyNameCell = new PdfPCell(new Phrase(headerData.getCompanyName(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        companyNameCell.setBorder(Rectangle.NO_BORDER);
        companyNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        logoTable.addCell(companyNameCell);

        PdfPCell leftSideCell = new PdfPCell(logoTable);
        leftSideCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(leftSideCell);

        // Right side: invoice details
        PdfPTable detailsTable = new PdfPTable(1);
        detailsTable.setWidthPercentage(100);

        detailsTable.addCell(createDetailCell("Invoice#: " + headerData.getInvoiceNumber()));
        detailsTable.addCell(createDetailCell("Service Date: " + headerData.getServiceDate()));
        detailsTable.addCell(createDetailCell("Due Date: " + headerData.getDueDate()));
        detailsTable.addCell(createDetailCell("Amount Due: " + headerData.getAmountDue()));

        PdfPCell detailsCell = new PdfPCell(detailsTable);
        detailsCell.setBorder(Rectangle.NO_BORDER);
        detailsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(detailsCell);
    }
}
