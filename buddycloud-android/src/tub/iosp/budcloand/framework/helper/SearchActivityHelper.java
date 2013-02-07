package tub.iosp.budcloand.framework.helper;

import java.util.List;

import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;

public interface SearchActivityHelper {
	public List<BCMetaData> search(String keyword);

	List<BCSubscribtion> getSubscribtions();
}
