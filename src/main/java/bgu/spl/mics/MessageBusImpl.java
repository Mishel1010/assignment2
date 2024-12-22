package bgu.spl.mics;
import java.util.AbstractMap;
import java.util.concurrent.*;
import java.util.Map;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	//fields
	private ConcurrentHashMap<Class<? extends Event>, ConcurrentLinkedQueue<MicroService>> events;
	private ConcurrentHashMap<Class<? extends Broadcast>, ConcurrentLinkedQueue<MicroService>> broadcasts;
	private  ConcurrentHashMap <MicroService, ConcurrentLinkedQueue<Message>> microservices;
	private  ConcurrentHashMap <MicroService, ConcurrentLinkedQueue<Class<? extends Message>>> subscripsions;

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		synchronized (this){
		if (events == null){
			events = new ConcurrentHashMap<Class<? extends Event>, ConcurrentLinkedQueue<MicroService>>();
		}}
		events.computeIfAbsent(type, key -> new ConcurrentLinkedQueue<>()).add(m);
		subscripsions.get(m).offer(type);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized(this){
			if (broadcasts == null){
				broadcasts = new ConcurrentHashMap<Class<? extends Broadcast>, ConcurrentLinkedQueue<MicroService>>();
		}}
		broadcasts.computeIfAbsent(type, key -> new ConcurrentLinkedQueue<>()).add(m);
		subscripsions.get(m).offer(type);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		
    }

	@Override
	public void sendBroadcast(Broadcast b) { //sends the broadcast to all of the subscribers
		for(MicroService mike: (broadcasts.get(b))) {
			microservices.get(mike).offer(b);
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
	}   

	@Override
	public void register(MicroService m) {
		microservices.put(m, new ConcurrentLinkedQueue());
	}

	@Override
	public void unregister(MicroService m) {
	   for (Class<? extends Message> note : subscripsions.get(m)){
		if (!(events.get(note).remove(m)){
		broadcasts.get(note).remove(m);
	   }	

		}
		
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