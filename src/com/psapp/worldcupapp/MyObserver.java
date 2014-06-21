package com.psapp.worldcupapp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.psapp.worldcupapp.models.Events;


public class MyObserver implements PropertyChangeListener {
	  public MyObserver(Events model) {
	    model.addChangeListener(this);
	  }

	  @Override
	  public void propertyChange(PropertyChangeEvent event) {
	    System.out.println("Changed property: " + event.getPropertyName() + " [old -> "
	      + event.getOldValue() + "] | [new -> " + event.getNewValue() +"]");
	  }
	} 
