package automata;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import graph.editor.Edge;
import graph.editor.Vertex;

public class ObservableAutomaton extends DeterministicAutomaton {

	public ObservableAutomaton(List<Edge> edges) throws NotDeterministInitialStateException,
			UnknownInitialStateException, NotDeterministInitialStateException {
		super(edges);
		// TODO Auto-generated constructor stub
	}

	private Observable observable = new Observable() {
		@Override
		public void notifyObservers(Object arg) {
			setChanged();
			super.notifyObservers(arg);
		}
	};

	@Override
	protected Vertex changeCurrentVertex(Edge t) {
		observable.notifyObservers(t);
		return super.changeCurrentVertex(t);
	}

	public void addObserver(Observer o) {
		observable.addObserver(o);
	}
}
