package org.isj.etats.dynamicreports;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Page;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.isj.traitementmetier.facade.CandidatFacade;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SimpleDynamicReport {

    public void printDynamicReport(
            String requete,
            List<String> colonnesAImprimer,
            String titre,
            String sousTitre,
            boolean printBackgroundOnOddRows,
            boolean useFullPageWidth,
            Page orientation) {

        try {
            FastReportBuilder drb = new FastReportBuilder();
            drb.setTitle(titre)
                    .setSubtitle(sousTitre)
                    .setPrintBackgroundOnOddRows(printBackgroundOnOddRows)
                    .setUseFullPageWidth(useFullPageWidth)
                    .setPageSizeAndOrientation(orientation)
                    .setWhenNoDataBlankPage()
                    .addFirstPageImageBanner("images/isj.jpg", 100, 100, ImageBanner.Alignment.Center);

            ResultSet resultSet = new CandidatFacade().getConnection().createStatement().executeQuery(requete);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                if (colonnesAImprimer.contains(resultSetMetaData.getColumnName(i))) {
                    drb.addColumn(
                            resultSetMetaData.getColumnLabel(i),
                            resultSetMetaData.getColumnName(i),
                            resultSetMetaData.getColumnClassName(i),
                            (orientation.equals(Page.Page_A4_Landscape()) ? 280 : 200) / colonnesAImprimer.size(),
                            false);
                }
            }
            resultSet.beforeFirst();
            JRDataSource ds = new JRResultSetDataSource(resultSet);
            JasperPrint jp = DynamicJasperHelper.generateJasperPrint(drb.build(), new ClassicLayoutManager(), ds);
            JasperViewer.viewReport(jp);    //finally display the report report
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new SimpleDynamicReport().printDynamicReport(
                "select * from candidat",
                Arrays.asList("code", "nom", "prenom", "classe", "date_naissance", "email", "telephone", "sexe"),
                "Liste des Candidats de l'ISJ",
                "Etat imprime à " + new Date(),
                true,
                true,
                Page.Page_A4_Landscape());
    }
}
