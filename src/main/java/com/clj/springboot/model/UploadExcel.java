package com.clj.springboot.model;

import java.util.List;

public class UploadExcel {
    private List<Object> list;

    private List<String> title;

    private String[][] content;

    private String sheetName;

    private List<Integer> cellRangeAddress;

    private Integer mergeRow;

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }


    public String[][] getContent() {
        return content;
    }

    public void setContent(String[][] content) {
        this.content = content;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<Integer> getCellRangeAddress() {
        return cellRangeAddress;
    }

    public void setCellRangeAddress(List<Integer> cellRangeAddress) {
        this.cellRangeAddress = cellRangeAddress;
    }

    public Integer getMergeRow() {
        return mergeRow;
    }

    public void setMergeRow(Integer mergeRow) {
        this.mergeRow = mergeRow;
    }

}
