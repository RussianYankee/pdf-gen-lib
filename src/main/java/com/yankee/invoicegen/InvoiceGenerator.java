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

            // Add header table
            document.add(composeHeaderTable(headerData));

            document.add(new Chunk("\n"));
            // Add customer details section
            document.add(composeCustomerTable(headerData));

            // Add table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2f, 0.5f, 1, 1});
            table.setSpacingBefore(20f);
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

                cell = createItemCell(String.format("%.2f", item.getUnitPrice()), Element.ALIGN_CENTER);
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

    private PdfPCell createDetailCellLeft(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 10)));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell createDetailCellRight(String text, String fontName) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(fontName, 10)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }

    private PdfPCell createItemCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
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

    private PdfPCell createCell(String text, int alignment, int border, String fontName, int fontSize) {
        PdfPCell cell = createCell(text, alignment, border);
        cell.setPhrase(new Phrase(text, FontFactory.getFont(fontName, fontSize)));
        return cell;
    }

    private void composeTableFooter(PdfPTable table, double subtotal, double tax, double total) {
        String[] footerLabels = {"Subtotal", "Tax", "Total"};
        double[] footerValues = {subtotal, tax, total};

        for (int i = 0; i < footerLabels.length; i++) {
            table.addCell(createCell("", Element.ALIGN_LEFT, Rectangle.NO_BORDER));
            table.addCell(createCell("", Element.ALIGN_LEFT, Rectangle.NO_BORDER));
            table.addCell(createCell(footerLabels[i], Element.ALIGN_LEFT, Rectangle.NO_BORDER));
            table.addCell(createCell(String.format("$ %.2f", footerValues[i]), Element.ALIGN_RIGHT, Rectangle.NO_BORDER, FontFactory.HELVETICA_BOLD, 12));
        }
    }

    private PdfPTable composeHeaderTable(InvoiceHeaderData headerData) throws IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 1});
        table.setWidthPercentage(100);

        // Left side: company logo and name
        PdfPTable logoTable = new PdfPTable(1);
        Image logo = Image.getInstance(headerData.getLogoPath());
        logo.scaleToFit(100, 100);
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
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);

        detailsTable.addCell(createDetailCellLeft("Invoice#: "));
        detailsTable.addCell(createDetailCellRight(headerData.getInvoiceNumber(), FontFactory.HELVETICA_BOLD));
        detailsTable.addCell(createDetailCellLeft("Service Date: "));
        detailsTable.addCell(createDetailCellRight(headerData.getServiceDate(), FontFactory.HELVETICA_BOLD));
        detailsTable.addCell(createDetailCellLeft("Due Date: "));
        detailsTable.addCell(createDetailCellRight(headerData.getDueDate(), FontFactory.HELVETICA_BOLD));
        detailsTable.addCell(createDetailCellLeft("Amount Due: "));
        detailsTable.addCell(createDetailCellRight(headerData.getAmountDue(), FontFactory.HELVETICA_BOLD));


        PdfPCell detailsCell = new PdfPCell(detailsTable);
        detailsCell.setBorder(Rectangle.NO_BORDER);
        detailsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(detailsCell);

        return table;
    }

    private PdfPTable composeCustomerTable(InvoiceHeaderData headerData) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(60);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidths(new int[]{2, 5});
        table.setSpacingBefore(20f);

        table.addCell(createDetailCellLeft("Customer: "));
        table.addCell(createDetailCellRight(headerData.getCustomerName(), FontFactory.HELVETICA));
        table.addCell(createDetailCellLeft("Billing Address: "));
        table.addCell(createDetailCellRight(headerData.getBillingAddress(), FontFactory.HELVETICA));
        table.addCell(createDetailCellLeft("Service Address: "));
        table.addCell(createDetailCellRight(headerData.getServiceAddress(), FontFactory.HELVETICA));

        return table;
    }
}
