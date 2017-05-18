package com.lxg.music.interfaces;

import com.lxg.music.model.MusicInfo;

import java.util.List;

public interface IQueryFinished {
	
	void onFinished(List<MusicInfo> list);

}
