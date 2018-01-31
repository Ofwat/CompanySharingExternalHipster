package uk.gov.ofwat.external.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.service.dto.data.CellDto;
import uk.gov.ofwat.external.service.dto.data.RowDto;
import uk.gov.ofwat.external.service.dto.data.TableDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Service
@Transactional
public class ExcelReaderService {

    public TableDto readFOut() {
        TableDto tableDto= new TableDto();

        try {
            File excelFile = new File("C:/Dev/CompanySharingExternalHipster/src/test/resources/dataUpload/test_fout_sheet_1.xlsx");

            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {
                RowDto rowDto = new RowDto();
                tableDto.getRows().add(rowDto);

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {
                    CellDto cellDto = new CellDto();
                    rowDto.getCells().add(cellDto);

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum will be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                        cellDto.setValue(currentCell.getStringCellValue());
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                        cellDto.setValue("" + currentCell.getNumericCellValue());
                    }
                }
                System.out.println();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return tableDto;
    }
}
