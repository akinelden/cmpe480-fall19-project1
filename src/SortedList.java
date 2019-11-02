import java.util.Comparator;
import java.util.LinkedList;

public class SortedList<E> extends LinkedList<E> {

	private Comparator<E> comparator;

	public SortedList(Comparator<E> comp){
		super();
		comparator = comp;
	}

	@Override
	public boolean add(E _e){
		int i=0;
		for(E element : this){
			if(comparator.compare(_e, element)<0){
				this.add(i, _e);
				return true;
			}
			i++;
		}
		return super.add(_e);
	}

}
