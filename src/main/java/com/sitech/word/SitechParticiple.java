package com.sitech.word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.sitech.word.core.category.Dic;
import com.sitech.word.core.segmentation.SegmentationAlgorithm;
import com.sitech.word.core.thread.CountableThreadPool;
import com.sitech.word.dic.IDic;
import com.sitech.word.dic.impl.DicImpl;
import com.sitech.word.pojo.Word;

public class SitechParticiple  implements Runnable, Task{
	protected static SegmentationAlgorithm segmentationAlgorithm;
	protected String uuid;
	protected Word word;
	public static char[] chars = {'，',',','、','。','？','?','！','!','“','”','‘','’','"','"'};
	
	protected AtomicInteger stat = new AtomicInteger(STAT_INIT);
    protected final static int STAT_INIT = 0;
    protected final static int STAT_RUNNING = 1;
    protected final static int STAT_STOPPED = 2;
    private ReentrantLock newUrlLock = new ReentrantLock();
    private Condition newUrlCondition = newUrlLock.newCondition();
    protected boolean exitWhenComplete = true;
    protected boolean destroyWhenExit = true;
    protected CountableThreadPool threadPool;
    protected int threadNum = 1;
    private static LinkedList<Word> textQueue = new LinkedList<Word>();
    
    private static SitechParticiple sitechParticiple;
    
    public SitechParticiple getSitechParticiple() {
		return sitechParticiple;
	}
	public void setSitechParticiple(SitechParticiple sitechParticiple) {
		this.sitechParticiple = sitechParticiple;
	}

	private int emptySleepTime = 30000;
	
	public SitechParticiple(SegmentationAlgorithm segmentationAlgorithm) {
		segmentationAlgorithm =  segmentationAlgorithm;
	}
	public SitechParticiple() {
		segmentationAlgorithm =  SegmentationAlgorithm.MaximumMatching;
	}

	public static SitechParticiple create(SegmentationAlgorithm segmentationAlgorithm) {
		sitechParticiple = new SitechParticiple(segmentationAlgorithm);
//		new Punctuation().loadResource();
		new Dic().loadResource();
        return sitechParticiple;
    }
	public static boolean is(char _char){
        int index = Arrays.binarySearch(chars, _char);
        System.out.println(_char);
        System.out.println("index:"+index);
        return index >= 0;
    }
	
	public static SitechParticiple addText(String text) {
		SitechParticiple sitech = null;
		if(segmentationAlgorithm!=null){
			sitech = sitechParticiple;
		}else{
			sitech = new SitechParticiple();
		}
        int start = 0;
        char[] array = text.toCharArray();
        int len = array.length;
        for(int i=0; i<len; i++){
            char c = array[i];
            if(is(c)){
                if(i > start){
                	textQueue.add(new Word(text.substring(start, i)));
                    start = i+1;
                }else{
                    start++;
                }
                if(true){
                	textQueue.add(new Word(Character.toString(c)));
                }
            }
        }
        if(len - start > 0){
        	textQueue.add(new Word(text.substring(start, len)));
        }
		return sitech;
	}

	@Override
	public String getUUID() {
		uuid = UUID.randomUUID().toString();
        return uuid;
	}

	@Override
	public Word getText() {
		return word;
	}

	@Override
	public void run() {
		checkRunningStat();
		initComponent();
		final IDic dics = new DicImpl();
		final List<String> value = new ArrayList<String>();
		while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
			final Word word = textQueue.size()>0?textQueue.pop():null;
			if(word==null){
				if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                    break;
                }
				waitNewRequest();
			}else{
				threadPool.execute(new Runnable() {
					public void run() {
						String text = word.getText();
						int len = text.length();
						for(int x = 0;x<len-1;x++){
							if(dics.contains(text.substring(0, len-x))){
								value.add(text.substring(0, len-x));
							}
						}
					}
				});
			}
		}
		System.out.println("分词："+value);
		stat.set(STAT_STOPPED);
		if (destroyWhenExit) {
			threadPool.shutdown();
        }
		
	}
	
	private void waitNewRequest() {
        newUrlLock.lock();
        try {
            if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                return;
            }
            newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        } finally {
            newUrlLock.unlock();
        }
    }
	
	private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("SitechParticiple is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }
	
	protected void initComponent() {
        if (threadPool == null || threadPool.isShutdown()) {
                threadPool = new CountableThreadPool(threadNum);
        }
    }
	
	public void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }
	
	 public void start() {
        runAsync();
	 }
	 
	 public static void main(String[] args) {
		 
	 }
}
