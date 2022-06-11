public class SwingObserverExample{
    JFrame frame;
    public static void main(String[] args) {
        //프레임을 만들고 그 안에 버튼을 추가하는 간단한 스윙 애플리케이션
        SwingObserverExample example = new SwingObserverExample();
        example.go();
    }

    public void go(){
        frame = new JFrame();

        JButton button = new JButton("할까?말까?");
        //람다식으로 구현
        button.addActionListener(event->
                System.out.println("하지마! 아마 후회할 걸?"));
        button.addActionListener(event->
                System.out.println("그냥 해 봐"));
    }
}