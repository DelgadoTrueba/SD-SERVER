package com.delgadotrueba.sd.servidorJuegoMonohilo2.models;

public class CellModel {

	////////////////////////////////////////////////////////////////////////////
	// Constant
	////////////////////////////////////////////////////////////////////////////
	
	
	// Cell types -> Config File
	private static final int MIN_TYPE_RANGE = 0;
	private static final int EMPTY_CELL_TYPE = 25;
	private static final int MAX_TYPE_RANGE = 26;
	
	////////////////////////////////////////////////////////////////////////////
	// Instance variables
	////////////////////////////////////////////////////////////////////////////
	private boolean mIsSelected = false;
	private boolean mIsMatched = false;
	private byte mType = EMPTY_CELL_TYPE;
	
	public CellModel(byte aType) {
		this.mType = aType;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Public Interface
	////////////////////////////////////////////////////////////////////////////
	
	public String getTypeString() {
		if(mType < 10) {
			return "0" + mType;
		}else {
			return ""+mType;	
		}
	}
	
	public byte getType() {
		return  mType;
	}
	
	
	public boolean sameType(CellModel other) {
		if (other == null) {
			ErrorHandler.error("CELL: ", "sameType(Cell) received null", false);
			return false;
		}
		
		if (this.getTypeString().equals(other.getTypeString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setSelected(boolean selected) {
		mIsSelected = selected;
	}
	
	public void setMatched(boolean matched) {
		mIsMatched = matched;
	}
	
	public boolean isSelected() {
		if (mIsSelected == true) {
			return true;
		}
		return false;
	}
	
	public boolean isMatched() {
		if (mIsMatched == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equals(CellModel other) {
	
		if (other == null) return false;
		
		if (mIsMatched != other.mIsMatched)
			return false;
		if (mIsSelected != other.mIsSelected)
			return false;
		if (mType != other.mType)
			return false;
		
		return true;
	}
	
}
