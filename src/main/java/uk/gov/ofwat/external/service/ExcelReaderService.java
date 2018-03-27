package uk.gov.ofwat.external.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.TableMetadata;
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

        public TableDto readFOut(String excelFilePath, Long reportId) {
        TableDto tableDto= new TableDto();
        tableDto.setId(reportId.intValue());
        int headerCellCount = 0;

        try {
            File excelFile = new File(excelFilePath);

            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheet("F_Outputs");

            int firstRowNo = datatypeSheet.getFirstRowNum();
            int lastRowNo = datatypeSheet.getLastRowNum();

            for (int rowNo=firstRowNo; rowNo<=lastRowNo; rowNo++) {

                RowDto rowDto = new RowDto();
                tableDto.getRows().add(rowDto);

                Row currentRow = datatypeSheet.getRow(rowNo);
                if (null == currentRow) {
                    for (int i=0; i<headerCellCount; i++) {
                    CellDto cellDto = new CellDto();
                        System.out.print("--");
                        cellDto.setValue("");
                    rowDto.getCells().add(cellDto);

                    }
                    System.out.println();
                    continue;
                }

                int lastCellNo = currentRow.getLastCellNum();
                if (1 == rowNo) {
                    headerCellCount = lastCellNo;
                }
                for (int cellNo=0; cellNo<lastCellNo; cellNo++) {
                    CellDto cellDto = new CellDto();
                    rowDto.getCells().add(cellDto);
                    Cell currentCell = currentRow.getCell(cellNo, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                        cellDto.setValue(currentCell.getStringCellValue());
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                        cellDto.setValue("" + currentCell.getNumericCellValue());
                    }
                    else if (currentCell.getCellTypeEnum() == CellType.BLANK) {
                        System.out.print("--");
                        cellDto.setValue("");
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

        addTrailingCells(tableDto.getRows().get(0), headerCellCount);

        return tableDto;
    }

    private void addTrailingCells(RowDto rowDto, int totalCellsRequired) {

        int cellNo = rowDto.getCells().size();
        cellNo++;
        for (; cellNo <= totalCellsRequired; cellNo++) {
            System.out.print("-" + cellNo + "-");
            CellDto cellDto = new CellDto();
            cellDto.setValue("");
            rowDto.getCells().add(cellDto);
        }
    }


}
