package bgu.spl.mics;
import java.util.AbstractMap;
import java.util.concurrent.*;
import java.util.Map

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	//fields
	private ConcurrentHashMap<Class<? extends Event>, ConcurrentLinkedQueue<MicroService>> events;
	private ConcurrentHashMap<Class<? extends Broadcast>, ConcurrentLinkedQueue<MicroService>> broadcasts;

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		events.computeIfAbsent(type, key -> new ConcurrentLinkedQueue<>()).add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		broadcasts.computeIfAbsent(type, key -> new ConcurrentLinkedQueue<>()).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future fut = new Future<T>();
		fut.resolve(result);
    }

	@Override
	public void sendBroadcast(Broadcast b) { //sends the broadcast to all of the subscribers
		for(MicroService mike: (broadcasts.get(b))) {
			mike.addMessage(b);
		}

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		ConcurrentLinkedQueue<MicroService> q = events.get(e);
		if (q.peek() == null){
			return null;
		}
		q.peek().addMessage(e);
		q.offer(q.poll());
		return new Future();
	}

	@Override
	public void register(MicroService m) {
		m.setMessageBus(this);
	}

	@Override
	public void unregister(MicroService m) {
		while(!(m.subscribedTo.isEmpty())){
			Map.Entry<Class<? extends Message>,Callback> entry = m.subscribedTo.poll();
			broadcasts.get(entry.getKey()).remove(m);
			events.get(entry.getKey()).remove(m);

		}
		m.subscribedTo = null;
	}

	@Override
	public synchronized Message awaitMessage(MicroService m)  {
		while (m.isfree()) { //has no messages in it's queue
			try{
				m.wait();
			}
			catch(InterruptedException ignored){}
			
		}
	return m.popmessage();
	}

	

}