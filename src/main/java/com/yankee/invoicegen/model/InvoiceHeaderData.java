package com.yankee.invoicegen.model;

public class InvoiceHeaderData {
    String logoPath;
    String companyName;
    String invoiceNumber;
    String serviceDate;
    String dueDate;
    String amountDue;
    String customerName;
    String billingAddress;
    String serviceAddress;

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

    public InvoiceHeaderData logoPath(String logoPath) {
        setLogoPath(logoPath);
        return this;
    }

    public InvoiceHeaderData companyName(String companyName) {
        setCompanyName(companyName);
        return this;
    }

    public InvoiceHeaderData invoiceNumber(String invoiceNumber) {
        setInvoiceNumber(invoiceNumber);
        return this;
    }

    public InvoiceHeaderData serviceDate(String serviceDate) {
        setServiceDate(serviceDate);
        return this;
    }

    public InvoiceHeaderData dueDate(String dueDate) {
        setDueDate(dueDate);
        return this;
    }

    public InvoiceHeaderData amountDue(String amountDue) {
        setAmountDue(amountDue);
        return this;
    }

    public InvoiceHeaderData customerName(String customerName) {
        setCustomerName(customerName);
        return this;
    }

    public InvoiceHeaderData billingAddress(String billingAddress) {
        setBillingAddress(billingAddress);
        return this;
    }

    public InvoiceHeaderData serviceAddress(String serviceAddress) {
        setServiceAddress(serviceAddress);
        return this;
    }

    public InvoiceHeaderData build() {
        return this;

    }
}
