package fr.esipe.game.ship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.jbox2d.common.Vec2;

public class Track {
	private Vec2 spawn;
	private int loop=-1;
	private final ArrayList<Vec2> track;
	public Track(Vec2 Spawn) {
		this.spawn=Spawn;
		track=new ArrayList<Vec2>();
	}
	
	public Track() {
		track=new ArrayList<Vec2>();
	}
	public void addAll(List<Vec2> Positions){
		track.addAll(Positions);
	}
	public void add(Vec2 newPosition){
		track.add(track.size(), newPosition);
	}
	
	public void setTrackLoop(int loopIndex){
		this.loop=loopIndex;
	}
	public int size(){
		return track.size();
	}
	public void clear(){
		track.clear();
	}
	public Vec2 getNextPos(int index){
		return track.get(index);
	}
	public Vec2 getSpawn() {
		return spawn;
	}
	
	public Iterator<Vec2> getIterator(){
		return new Iterator<Vec2>() {
			int cur=-1;
			@Override
			public boolean hasNext() {
				if(cur+1>=track.size()&&loop==-1)return false;
				return true;
			}

			@Override
			public Vec2 next() {
				if(!hasNext())throw new NoSuchElementException();
				cur++;
				if(cur==track.size()&&loop!=-1)cur=loop;
				return track.get(cur);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public void startx(Vec2 startX) {
		spawn = startX;
		
	}
	
	
}
