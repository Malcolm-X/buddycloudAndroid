package tub.iosp.budcloand.framework.types;

import java.util.Date;

public interface BCStamped {
	public int compareDate(BCStamped stampedEntity);
	public Date getDate();
	public void setDate(Date date);
}
