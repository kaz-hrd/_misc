package com.view.conv;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static int MAX_WIDTH = 1140;
    private static int SIDE_MARGIN = 12;
    private static int TARGET_WIDTH;

    private static int ALLOWABLE_LIMIT_X = 5;
    private static int ALLOWABLE_LIMIT_Y = 5;

    private static int CELL_NUM = 24;
    private static int CELL_WIDTH;

    static {
        recalTargetWidth();
        recalCellWidth();
    }

    public static void print() {
        logger.info("横幅 : " + getBaseWidth() + " (MAX_WIDTH=" + MAX_WIDTH + ", SIDE_MARGIN=" + SIDE_MARGIN + ")");
        logger.info("縦方向（Y軸）許容誤差： " + ALLOWABLE_LIMIT_Y + ",  横方向（X軸）許容誤差： " + ALLOWABLE_LIMIT_X);
        logger.info("セル幅： " + CELL_WIDTH);
    }

    public static void configure(Properties props) {
        if(props.getProperty("MAX_WIDTH") != null && !props.getProperty("MAX_WIDTH").isEmpty()) {
            setMaxWidth(Integer.parseInt(props.getProperty("MAX_WIDTH")));
        }
        if(props.getProperty("SIDE_MARGIN") != null && !props.getProperty("SIDE_MARGIN").isEmpty()) {
            setSideMargin(Integer.parseInt(props.getProperty("SIDE_MARGIN")));
        }
        if(props.getProperty("ALLOWABLE_LIMIT_X") != null && !props.getProperty("ALLOWABLE_LIMIT_X").isEmpty()) {
            setAllowableLimitX(Integer.parseInt(props.getProperty("ALLOWABLE_LIMIT_X")));
        }
        if(props.getProperty("ALLOWABLE_LIMIT_Y") != null && !props.getProperty("ALLOWABLE_LIMIT_Y").isEmpty()) {
            setAllowableLimitY(Integer.parseInt(props.getProperty("ALLOWABLE_LIMIT_Y")));
        }
        if(props.getProperty("CELL_NUM") != null && !props.getProperty("CELL_NUM").isEmpty()) {
            setCellNum(Integer.parseInt(props.getProperty("CELL_NUM")));
        }
    }

    /** 返還対象の画面の横幅 */
    public static int getBaseWidth() {
        return TARGET_WIDTH;
    }
    public static void setMaxWidth(int width) {
        MAX_WIDTH = width;
        recalTargetWidth();
        recalCellWidth();
    }
    public static void setSideMargin(int margin) {
        SIDE_MARGIN = margin;
        recalTargetWidth();
        recalCellWidth();
    }
    private static void recalTargetWidth() {
        TARGET_WIDTH = MAX_WIDTH - SIDE_MARGIN;
    }

    /** X軸方向許容誤差 */
    public static int getAllowableLimitX() {
        return ALLOWABLE_LIMIT_X;
    }
    public static void setAllowableLimitX(int x) {
        ALLOWABLE_LIMIT_X = x;
    }

    /** Y軸方向許容誤差 */
    public static int getAllowableLimitY() {
        return ALLOWABLE_LIMIT_Y;
    }
    public static void setAllowableLimitY(int y) {
        ALLOWABLE_LIMIT_Y = y;
    }

    /** 1行あたりのセル数（列数） */
    public static int getCellNum() {
        return CELL_NUM;
    }
    public static void setCellNum(int cellNum) {
        CELL_NUM = cellNum;
        recalCellWidth();
    }

    /** セル１つあたりの横幅 */
    public static int getCellWidth() {
        return CELL_WIDTH;
    }
    private static void recalCellWidth() {
        CELL_WIDTH = TARGET_WIDTH / CELL_NUM;
    }

}
