package com.DAO;
// 로직 컨트롤러 

import java.util.Random;
import java.util.Scanner;


public class PlayerDAO {
	Random rand = new Random();
	Scanner sc = new Scanner(System.in);
	
	private String Pname = null;
	private int Plevel = 0;
	private int Peng = 100;
	private String id = null;
	private String pw = null;
	private int Pexp = 0;
	
	private boolean result = false;
	
	//1. 피카츄 요구사항 (배고파 심심해 뻐근해 등) 출력, 피카츄 육성(밥먹기 놀기 등) 문장 선택 후 비교, 그 후 경험치 증가와 에너지 증감
	   public void PikaPt() {
	         while(true) {
	            String[] desire = {"자고 싶어", "배고파", "심심해", "뻐근해", "게임 종료"}; //피카츄 요구사항 + 게임종료 배열
	            String[] train = {"잠자기", "밥먹기", "놀기", "운동하기", "게임을 종료합니다"}; //피카츄 키우기 + 게임종료 배열
	            String[] move = {"쿨쿨(_ _).zZ","냠냠","히히재밌땅","운동하기싫어힝힝"};
	            while(true) {//게임 종료 들어올 때까지 육성 계속 실행
	               
	               int num1 = rand.nextInt(4);
	               System.out.println(desire[num1]);
	               
	               for(int i = 0; i<train.length; i++) {
	                  System.out.print("["+ (i+1) +"]"+ train[i]+" ");
	               }
	                  System.out.print("메뉴선택 >> ");
	               int num2 = sc.nextInt() - 1; //인덱스는 0부터 시작하기 때문에 입력 받은 값에서 1을 빼준 후 넣어준다.
	               System.out.println(move[num2]);
	              
	               //[5] 게임종료 선택시 종료
	               if(num1==4) {
	                  break;
	               //3. 피카츄 요구사항 피카츄 육성문장 비교 (1번 2번 인덱스 위치 비교)
	               }else if(num1==num2) {
	            	   int Upexp = 1+rand.nextInt(20);
	            	   Pexp += Upexp;
	                  System.out.println("경험치가"+Upexp+" 증가하여"+Pexp+"이 되었습니다.");
	                  
	                  if(Pexp>=100) {
	                     Plevel+=1;
	                     System.out.println("경험치가 100이 되어 레벨이 올랐습니다.");
	                  }
	               }else if(num1!=num2) {
	                  System.out.println("피카츄가 화를냅니다.");
	               }
	               
	               if(num2==1 || num2==2) {
	            	  Peng +=10;
	                  System.out.println("에너지가 10 증가하여"+Peng+"이 되었습니다.");
	                  
	               }else if(num2==3 || num2==4) {
	            	   Peng -=10;
	                  System.out.println("에너지가 10 감소하여"+Peng+"이 되었습니다.");
	                  
	                  if(Peng<=0) {
	                     System.out.println("에너지가 0이하가 되어 피카츄가 사망했습니다.");
	                     break;
	                  }
	               }
	            }
	         }
	      }
	//5. 게임 종료
	public void Exit() {
		
	}


}
