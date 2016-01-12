package it.apriori.core;

import java.util.List;

import it.apriori.core.dwdomain.ItemSet;

public interface AprioriInterface {
	public void generateFrequentItemSetFirstLevel();
	public Boolean generateFrequentItemSet(Integer level);
	public void generateRule();
	public List<ItemSet> getNextLevel(List<ItemSet> itemSetList);
	
}
