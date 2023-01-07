package org.sid.banquetechcodec.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.sid.banquetechcodec.entities.Compte;
import org.sid.banquetechcodec.entities.CompteCourant;
import org.sid.banquetechcodec.entities.CompteEpargne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ComptePdf {

    private static Logger logger = LoggerFactory.getLogger(ComptePdf.class);

    public static ByteArrayInputStream comptePdf(Compte compte){

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{
            PdfWriter writer =PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Customer Table", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            File file = new File(System.getProperty("user.home")+"/cinema/images/"+compte.getUser().getPhoto());
            Image img = Image.getInstance(String.format(file.toString(), "0120903"));

            img.scaleToFit(200, 100);


            PdfPTable table = new PdfPTable(3);
            table.setWidths(new float[] {2,2,2});

            table.setWidthPercentage(100);
            table.setSpacingBefore(5);

            PdfPCell imgcell1 = new PdfPCell(img);
            imgcell1.setRowspan(5);//on definir cell3 sur 4 lignes
            imgcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(imgcell1);


            PdfPCell typecell = new PdfPCell(new Phrase("Type:",FilmFonts.BOLD));
            typecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            typecell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(typecell);

            if(compte instanceof CompteCourant){
                PdfPCell typevalcell = new PdfPCell(new Phrase("Courant",FilmFonts.ITALIC));
                typevalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(typevalcell);
            }

            if(compte instanceof CompteEpargne){
                PdfPCell typevalcell = new PdfPCell(new Phrase("Epargne",FilmFonts.ITALIC));
                typevalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(typevalcell);
            }


            PdfPCell nomcell = new PdfPCell(new Phrase("Nom:",FilmFonts.BOLD));
            nomcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            nomcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(nomcell);


            PdfPCell nomvalcell = new PdfPCell(new Phrase(compte.getUser().getUsername(),FilmFonts.ITALIC));
            nomvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(nomvalcell);


            PdfPCell emailcell = new PdfPCell(new Phrase("Email:",FilmFonts.BOLD));
            emailcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            emailcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(emailcell);


            PdfPCell emailvalcell = new PdfPCell(new Phrase(compte.getUser().getEmail(),FilmFonts.ITALIC));
            emailvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(emailvalcell);


            PdfPCell soldecell = new PdfPCell(new Phrase("Solde:",FilmFonts.BOLD));
            soldecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            soldecell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(soldecell);


            PdfPCell soldevalcell = new PdfPCell(new Phrase(String.valueOf(compte.getSolde()),FilmFonts.ITALIC));
            soldevalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(soldevalcell);


            if(compte instanceof CompteCourant){
                PdfPCell decouvertcell = new PdfPCell(new Phrase("Decouvert:",FilmFonts.BOLD));
                decouvertcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                decouvertcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(decouvertcell);


                PdfPCell decourvertvalcell = new PdfPCell(new Phrase(String.valueOf(((CompteCourant) compte).getDecouvert()),FilmFonts.ITALIC));
                decourvertvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(decourvertvalcell);
            }


            if(compte instanceof CompteEpargne){
                PdfPCell decouvertcell = new PdfPCell(new Phrase("Taux:",FilmFonts.BOLD));
                decouvertcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                decouvertcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(decouvertcell);


                PdfPCell decourvertvalcell = new PdfPCell(new Phrase(String.valueOf(((CompteEpargne) compte).getTaux()),FilmFonts.ITALIC));
                decourvertvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(decourvertvalcell);
            }


            PdfPCell numerocell = new PdfPCell(new Phrase("numero compte:", FilmFonts.BOLD));
            numerocell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            numerocell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(numerocell);


            PdfPCell numerovalcell = new PdfPCell(new Phrase(compte.getCodeCp(), FilmFonts.BOLDITALIC));
            numerovalcell.setColspan(2);//position sur 2 collone
            numerovalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(numerovalcell);

            PdfPCell datecell = new PdfPCell(new Phrase("Date creation compte:", FilmFonts.BOLD));
            datecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            datecell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(datecell);


            PdfPCell datevalcell = new PdfPCell(new Phrase(String.valueOf(compte.getDateCreationCp()), FilmFonts.BOLDITALIC));
            datevalcell.setColspan(2);//position sur 2 collone
            datevalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(datevalcell);

            PdfPCell operationcell = new PdfPCell(new Phrase("Retrait:", FilmFonts.BOLD));
            operationcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            operationcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(operationcell);


            PdfPCell operationvalcell = new PdfPCell(new Phrase("21548", FilmFonts.BOLDITALIC));
            operationvalcell.setColspan(2);//position sur 2 collone
            operationvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(operationvalcell);



            PdfPCell dateOperationcell = new PdfPCell(new Phrase("Date Operation:", FilmFonts.BOLD));
            dateOperationcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            dateOperationcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(dateOperationcell);


            PdfPCell dateOperationvalcell = new PdfPCell(new Phrase("12-12-2021", FilmFonts.BOLDITALIC));
            dateOperationvalcell.setColspan(2);//position sur 2 collone
            dateOperationvalcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(dateOperationvalcell);

            table.setTotalWidth(100);
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            logger.error(e.toString());
        } catch (MalformedURLException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
