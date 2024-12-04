package com.yankee.invoicegen.model;

import java.util.List;

public class InvoiceData {
    private String logoPath;
    private String companyName;
    private String invoiceNumber;
    private String serviceDate;
    private String dueDate;
    private String amountDue;
    private String customerName;
    private String billingAddress;
    private String serviceAddress;
    private double taxRate;

    private List<InvoiceItem> invoiceItems;

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public InvoiceData logoPath(String logoPath) {
        setLogoPath(logoPath);
        return this;
    }

    public InvoiceData companyName(String companyName) {
        setCompanyName(companyName);
        return this;
    }

    public InvoiceData invoiceNumber(String invoiceNumber) {
        setInvoiceNumber(invoiceNumber);
        return this;
    }

    public InvoiceData serviceDate(String serviceDate) {
        setServiceDate(serviceDate);
        return this;
    }

    public InvoiceData dueDate(String dueDate) {
        setDueDate(dueDate);
        return this;
    }

    public InvoiceData amountDue(String amountDue) {
        setAmountDue(amountDue);
        return this;
    }

    public InvoiceData customerName(String customerName) {
        setCustomerName(customerName);
        return this;
    }

    public InvoiceData billingAddress(String billingAddress) {
        setBillingAddress(billingAddress);
        return this;
    }

    public InvoiceData serviceAddress(String serviceAddress) {
        setServiceAddress(serviceAddress);
        return this;
    }

    public InvoiceData build() {
        return this;

    }

    public InvoiceData taxRate(double taxRate) {
        setTaxRate(taxRate);
        return this;
    }

    public InvoiceData items(List<InvoiceItem> items) {
        setInvoiceItems(items);
        return this;
    }
}
