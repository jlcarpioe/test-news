package com.github.jlcarpioe.testnews.interfaces;

import com.github.jlcarpioe.testnews.models.Story;


/**
 * OnItemListListener
 * Actions listener for a story selected.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>
 *
 */
public interface OnItemListListener {

	void onSelected(Story story);

}
