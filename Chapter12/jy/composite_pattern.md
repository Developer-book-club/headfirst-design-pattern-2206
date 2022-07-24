## 복합 패턴
2개 이상의 패턴을 결합해 일반적으로 자주 등장하는 문제들의 해법 제공

### MVC Pattern

Model : 모든 데이터, 상태와 애플리케이션 로직이 들어있음. 뷰와 컨트롤러에서 모델의 상태를 조작하거나 가져올 때 필요한 인터페이스를 제공하고, 모델이 자신의 상태 변화를 옵저버들에게 연락해 주긴 하지만, 기본적으로 모델은 뷰와 컨트롤러에 관심이 없음. <옵저버 패턴 사용>

View : 모델을 표현하는 방법을 제공함. 일반적으로 화면에 표시할 때 필요한 상태와 데이터는 모델에서 직접 가져옴. <컴포지트 패턴 사용>

Controller : 사용자로부터 입력을 받으며 입력받은 내용이 모델에게 어던 의미가 있는 지 파악함. <전략 패턴 사용>


- View- Client - Controller - Model
모델 -> 뷰 '상태 변화 통지'
모델에서 뷰에게 상태가 변경됐음을 아린다.
모델은 파일을 관리함.

뷰 -> 클라이언트 '뷰 디스플레이 갱신'
디스플레이가 갱신되는 것을 볼 수 있고, 음악이 재생되는 것을 들을 수 있음.

클라이언트 -> 컨트롤러
사용자가 인터페이스를 건드리면 그 행동이 컨트롤러에게 전달됨.

컨트롤러 -> 모델
컨트롤러에서 플레이어 모델에게 음악 재생 요청

1. 사용자는 뷰에만 접촉 가능
2. 컨트롤러가 모델에게 상태를 변경하라고 요청
3. 컨트롤러가 뷰를 변경해 달라고 요청 가능
4. 상태가 변경되면 모데링 뷰에 그 사실을 알림
5. 뷰가 모델에게 상태를 요청함

```
package headfirst.designpatterns.combined.djview;
  
public interface BeatModelInterface {
//컨트롤러가 모델에게 사용자 입력을 전달할 때 사용

	void initialize(); //beatModel 인터페이스가 만들어질 때 호출되는 메소드
  
	void on();
  
	void off();
  
    void setBPM(int bpm);
    //BPM 설정 - 이 메소드가 호출되면 BPM이 바로 바뀜.
  
	int getBPM(); 
    //현재 BPM 리턴. 비트 생성자가 꺼져 있으면 0을 리턴.
  
	void registerObserver(BeatObserver o);
  
	void removeObserver(BeatObserver o);
  
	void registerObserver(BPMObserver o);
  
	void removeObserver(BPMObserver o);
}

```

### 모델 만들기

```
package headfirst.designpatterns.combined.djview;

import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import javax.sound.sampled.Line;

public class BeatModel implements BeatModelInterface, Runnable {
	List<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
	List<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
	int bpm = 90;
	Thread thread;
	boolean stop = false;
	Clip clip; //비트용으로 재생하는 오디오 클립

	public void initialize() {//비트 트랙을 설정
		try {
			File resource = new File("clap.wav");
			clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(AudioSystem.getAudioInputStream(resource));
		}
		catch(Exception ex) {
			System.out.println("Error: Can't load clip");
			System.out.println(ex);
		}
	}

	public void on() {//BPM을 기본값으로 설정한 다음 비트를 재생하는 스레드 시작
		bpm = 90;
		//notifyBPMObservers();
		thread = new Thread(this); //비트를 재생하는 스레드 시작
		stop = false;
		thread.start();
	}

	public void off() {
    //BPM을 0으로 설정하고, 비트를 재생하는 스레드를 멈춰서 음악을 끔
		stopBeat();
		stop = true;
	}
   

	public void run() {
    //바로 스레드를 실행해 BPM 값에 맞춰 음악을 시작한 다음 옵저버에게 비트 시작을 알려줌
		while (!stop) {
			playBeat();
			notifyBeatObservers();
			try {
				Thread.sleep(60000/getBPM());
			} catch (Exception e) {}
		}
	}

	public void setBPM(int bpm) {
    //컨트롤러에서 비트 조작 가능
		this.bpm = bpm;
		notifyBPMObservers();
	}

	public int getBPM() {
		return bpm;
	}

	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	public void notifyBeatObservers() {
		for(int i = 0; i < beatObservers.size(); i++) {
			BeatObserver observer = (BeatObserver)beatObservers.get(i);
			observer.updateBeat();
		}
	}

	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	public void notifyBPMObservers() {
		for(int i = 0; i < bpmObservers.size(); i++) {
			BPMObserver observer = (BPMObserver)bpmObservers.get(i);
			observer.updateBPM();
		}
	}

	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0) {
			beatObservers.remove(i);
		}
	}

	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i >= 0) {
			bpmObservers.remove(i);
		}
	}

	public void playBeat() {
		clip.setFramePosition(0);
		clip.start();
	}
	public void stopBeat() {
		clip.setFramePosition(0);
		clip.stop();
	}

}

```

### 뷰 만들기

```
package headfirst.designpatterns.combined.djview;
    
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DJView implements ActionListener,  BeatObserver, BPMObserver {
	BeatModelInterface model;
	ControllerInterface controller;
    JFrame viewFrame;
    JPanel viewPanel;
	BeatBar beatBar;
	JLabel bpmOutputLabel;
    JFrame controlFrame;
    JPanel controlPanel;
    JLabel bpmLabel;
    JTextField bpmTextField;
    JButton setBPMButton;
    JButton increaseBPMButton;
    JButton decreaseBPMButton;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem startMenuItem;
    JMenuItem stopMenuItem;

    public DJView(ControllerInterface controller, BeatModelInterface model) {	
		this.controller = controller;
		this.model = model;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
    }
    
    public void createView() {
		// Create all Swing components here
        viewPanel = new JPanel(new GridLayout(1, 2));
        viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setSize(new Dimension(100, 80));
        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		beatBar = new BeatBar();
		beatBar.setValue(0);
        JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
		bpmPanel.add(beatBar);
        bpmPanel.add(bpmOutputLabel);
        viewPanel.add(bpmPanel);
        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
	}
  
  
    public void createControls() {
		// Create all Swing components here
        JFrame.setDefaultLookAndFeelDecorated(true);
        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100, 80));

        controlPanel = new JPanel(new GridLayout(1, 2));

        menuBar = new JMenuBar();
        menu = new JMenu("DJ Control");
        startMenuItem = new JMenuItem("Start");
        menu.add(startMenuItem);
        startMenuItem.addActionListener((event) -> controller.start());
        // was....
        /*
        startMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.start();
            }
        });
        */
        stopMenuItem = new JMenuItem("Stop");
        menu.add(stopMenuItem); 
        stopMenuItem.addActionListener((event) -> controller.stop());
        // was...
        /*
        stopMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.stop();
            }
        });
        */
        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener((event) -> System.exit(0));
        // was...
        /*
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        */

        menu.add(exit);
        menuBar.add(menu);
        controlFrame.setJMenuBar(menuBar);

        bpmTextField = new JTextField(2);
        bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
        setBPMButton = new JButton("Set");
        setBPMButton.setSize(new Dimension(10,40));
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");
        setBPMButton.addActionListener(this);
        increaseBPMButton.addActionListener(this);
        decreaseBPMButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(increaseBPMButton);

        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bpmLabel);
        enterPanel.add(bpmTextField);
        JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);
        controlPanel.add(insideControlPanel);
        
        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlFrame.pack();
        controlFrame.setVisible(true);
    }

	public void enableStopMenuItem() {
    	stopMenuItem.setEnabled(true);
	}

	public void disableStopMenuItem() {
    	stopMenuItem.setEnabled(false);
	}

	public void enableStartMenuItem() {
    	startMenuItem.setEnabled(true);
	}

	public void disableStartMenuItem() {
    	startMenuItem.setEnabled(false);
	}

    public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setBPMButton) {
			int bpm = 90;
			String bpmText = bpmTextField.getText();
			if (bpmText == null || bpmText.contentEquals("")) {
				bpm = 90;
			} else {
				bpm = Integer.parseInt(bpmTextField.getText());
			}
        	controller.setBPM(bpm);
		} else if (event.getSource() == increaseBPMButton) {
			controller.increaseBPM();
		} else if (event.getSource() == decreaseBPMButton) {
			controller.decreaseBPM();
		}
    }

	public void updateBPM() {
		if (model != null) {
			int bpm = model.getBPM();
			if (bpm == 0) {
				if (bpmOutputLabel != null) {
        			bpmOutputLabel.setText("offline");
				}
			} else {
				if (bpmOutputLabel != null) {
        			bpmOutputLabel.setText("Current BPM: " + model.getBPM());
				}
			}
		}
	}
  
	public void updateBeat() {
		if (beatBar != null) {
			 beatBar.setValue(100);
		}
	}
}

```

### 비트 컨트롤러

```
package headfirst.designpatterns.combined.djview;
  
public class BeatController implements ControllerInterface {
	BeatModelInterface model;
	DJView view;
   
	public BeatController(BeatModelInterface model) {
		this.model = model;
		view = new DJView(this, model);
        view.createView();
        view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.initialize();
	}
  
	public void start() {
		model.on();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}
  
	public void stop() {
		model.off();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
	}
    
	public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
	}
    
	public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
  	}
  
 	public void setBPM(int bpm) {
		model.setBPM(bpm);
	}
}

```

### 모델, 뷰, 컨트롤러 코드 합치기

```
package headfirst.designpatterns.combined.djview;
  
public class DJTestDrive {

    public static void main (String[] args) {
        BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
    }
}

```



```
package headfirst.designpatterns.combined.djview;
  
public interface ControllerInterface {
	void start();
	void stop();
	void increaseBPM();
	void decreaseBPM();
 	void setBPM(int bpm);
}

```

### 심박 모니터 모델 적용시키기

```
package headfirst.designpatterns.combined.djview;

public class HeartAdapter implements BeatModelInterface {
	HeartModelInterface heart;
 
	public HeartAdapter(HeartModelInterface heart) {
		this.heart = heart;
	}

    public void initialize() {}
  
    public void on() {}
  
    public void off() {}
   
	public int getBPM() {
		return heart.getHeartRate();
	}
  
    public void setBPM(int bpm) {}
   
	public void registerObserver(BeatObserver o) {
		heart.registerObserver(o);
	}
    
	public void removeObserver(BeatObserver o) {
		heart.removeObserver(o);
	}
     
	public void registerObserver(BPMObserver o) {
		heart.registerObserver(o);
	}
  
	public void removeObserver(BPMObserver o) {
		heart.removeObserver(o);
	}
}

```

#### 심박 모니터 컨트롤러 만들기

```
package headfirst.designpatterns.combined.djview;
  
public class HeartController implements ControllerInterface {
	HeartModelInterface model;
	DJView view;
  
	public HeartController(HeartModelInterface model) {
		this.model = model;
		view = new DJView(this, new HeartAdapter(model));
        view.createView();
        view.createControls();
		view.disableStopMenuItem();
		view.disableStartMenuItem();
	}
  
	public void start() {}
 
	public void stop() {}
    
	public void increaseBPM() {}
    
	public void decreaseBPM() {}
  
 	public void setBPM(int bpm) {}
}

```

#### 테스트 코드
```
package headfirst.designpatterns.combined.djview;
  
public class HeartTestDrive {

    public static void main (String[] args) {
		HeartModel heartModel = new HeartModel();
        ControllerInterface model = new HeartController(heartModel);
    }
}

```

## Summary
- MVC는 옵저버 , 전략, 컴포지트 패턴으로 이뤄진 복합 패턴
- 모델은 옵저버 패턴을 사용해 의존성을 없애면서도 옵저버들에게 자신의 상택 변경되었음을 알릴 수 있음
- 모델은 옵저버 패턴을 사용해서 의존성을 없애면서도 옵저버들에게 자신의 상태가 변경되었음을 알릴 수 있음
- 컨트롤러는 뷰의 전략 객체. 뷰는 컨트롤러를 바꿔서 또 다른 행동 가능.
- 뷰는 컴포지트 패턴을 사용해 사용자 인터페이스를 구현함. 보통 패널이나 프레임, 버튼과 같은 중첩된 구성 요소로 이뤄짐.
- 모델, 뷰, 컨트롤러는 방금 말한 3가지 패턴으로 서로 느슨하게 결합되므로 깔끔하면서도 유연한 구현 가능
- 새로운 모델을 기존의 뷰와 컨트롤러에 연결해서 쓸 때는 어댑터 패턴을 활용.
- 클라이언트-서버 애플리케이션 구조에 MVC를 적응시켜 주는 다양합 웹 MVC 프레임워크가 있음.
