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
import java.io.IOException;

@Service
@Transactional
public class ExcelReaderService {

    public TableDto readFOut(File excelFile, Long reportId)   {
        TableDto tableDto = new TableDto();
        tableDto.setId(reportId.intValue());
        int headerCellCount = 0;
        try (Workbook workbook = new XSSFWorkbook(excelFile)) {


            Sheet datatypeSheet = workbook.getSheet("F_Outputs");
            int firstRowNo = datatypeSheet.getFirstRowNum();
            int lastRowNo = datatypeSheet.getLastRowNum();
            for (int rowNo = firstRowNo; rowNo <= lastRowNo; rowNo++) {

                RowDto rowDto = new RowDto();
                tableDto.getRows().add(rowDto);

                Row currentRow = datatypeSheet.getRow(rowNo);
                if (null == currentRow) {
                    makeBlankRow(headerCellCount, rowNo, rowDto);
                    continue;
                }

                int lastCellNo = currentRow.getLastCellNum();
                if (1 == rowNo) {
                    headerCellCount = lastCellNo;
                }
                for (int cellNo = 0; cellNo < lastCellNo; cellNo++) {
                    Cell currentCell = readExcelCell(currentRow, cellNo);
                    CellDto cellDto = makeCell(rowNo, cellNo, currentCell);
                    rowDto.getCells().add(cellDto);
                }
            }
        } catch (IOException |InvalidFormatException e) {
            e.printStackTrace();
        }
        addTrailingCellsToRow(tableDto, 0, headerCellCount);
        printTableDto(tableDto);
        return tableDto;
    }

    private void printTableDto(TableDto tableDto) {
        System.out.println();
        for (RowDto rowDto: tableDto.getRows()) {
            for (CellDto cellDto: rowDto.getCells()) {
                System.out.print("" + cellDto.getRow() + ":" + cellDto.getCol() + "[" + cellDto.getValue() + "]");
            }
            System.out.println();
        }
    }

    private void makeBlankRow(int headerCellCount, int rowNo, RowDto rowDto) {
        for (int cellNo = 0; cellNo < headerCellCount; cellNo++) {
            CellDto cellDto = buildCellDto(rowNo, cellNo);
            cellDto.setValue("");
            rowDto.getCells().add(cellDto);
        }
    }

    private CellDto makeCell(int rowNo, int cellNo, Cell currentCell) {
        CellDto cellDto = buildCellDto(rowNo, cellNo);

        if (currentCell.getCellTypeEnum() == CellType.STRING) {
            cellDto.setValue(currentCell.getStringCellValue());
        } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
            cellDto.setValue("" + currentCell.getNumericCellValue());
        } else if (currentCell.getCellTypeEnum() == CellType.BLANK) {
            cellDto.setValue("");
        }
        return cellDto;
    }

    private CellDto buildCellDto(int rowNo, int cellNo) {
        CellDto cellDto = new CellDto();
        cellDto.setRow(rowNo);
        cellDto.setCol(cellNo);
        return cellDto;
    }

    private Cell readExcelCell(Row currentRow, int cellNo) {
        return currentRow.getCell(cellNo, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
    }

    private void addTrailingCellsToRow(TableDto tableDto, int rowNo, int headerCellCount) {
        RowDto rowDto = tableDto.getRows().get(0);
        int cellNo = rowDto.getCells().size();
        for (; cellNo < headerCellCount; cellNo++) {
            CellDto cellDto = buildCellDto(rowNo, cellNo);
            cellDto.setValue("");
            rowDto.getCells().add(cellDto);
        }
    }


}
