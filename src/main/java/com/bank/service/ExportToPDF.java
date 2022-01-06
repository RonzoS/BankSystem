package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Transferlog;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.text.DecimalFormat;

import java.time.format.DateTimeFormatter;

public class ExportToPDF{

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void generatePDF(Account account, Transferlog transferlog) throws Exception{
        String fileName = "files/Transfer " + transferlog.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm")) + ".pdf";
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        writeLine(contentStream, 230, 740, null, 14, "Confirmation of payment");
        writeLine(contentStream, 50, 640, null, 12, "Your Account");
        writeLine(contentStream, 400, 640, null, 12, "Other Account");
        writeLine(contentStream, 50, 600, null, 12, String.valueOf(account.getAccountNumber()));
        if(transferlog.getOtherAccount() == 0)
            writeLine(contentStream, 400, 600, null, 12, "ATM");
        else
            writeLine(contentStream, 400, 600, null, 12, String.valueOf(transferlog.getOtherAccount()));
        if(transferlog.getValueAfter() < transferlog.getValueBefore())
            writeLine(contentStream, 50, 540, null, 14, "Payment");
        else
            writeLine(contentStream, 50, 540, null, 14, "Revenue");
        writeLine(contentStream, 50, 500, null, 12, "Date: " + transferlog.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        writeLine(contentStream, 50, 480, null, 12, "Value: " + df.format(transferlog.getValue()));
        writeLine(contentStream, 50, 460, null, 12, "Description: " + transferlog.getDescription());
        contentStream.close();

        document.save(fileName);
        document.close();

    }

    private static void writeLine(PDPageContentStream contentStream, int tx, int ty, PDFont font, int fontSize, String text){
        if(font==null)
            font = PDType1Font.HELVETICA;
        try{
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(tx, ty);
            contentStream.showText(text);
            contentStream.endText();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
