package tub.iosp.budcloand.framework.helper;

import java.util.List;

import tub.iosp.budcloand.framework.types.BCMetaData;

public interface SubscribtionActivityHelper {

	public List<BCMetaData> search(String[] keywords);

	public boolean subscribe(String channel);

}