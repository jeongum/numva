package com.egongil.numva_android_app.src.cert_phone.models;

import java.util.Random;

public class SendSMS {

    /***
     * SMS 발송 기능
     *
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(String phoneNumber, String message){

    }


    /***
     * 인증번호 생성 기능
     * @param len : 생성할 난수의 길이
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2: 중복제거)
     */
    public String numberGen(int len, int dupCd){
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++){
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));

            if(dupCd==1){
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2){
                //중복을 허용하지 않을 시 중복된 값이 있는지 검사
                if(!numStr.contains(ran)){
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else{
//                    생성된 난수가 중복되면 루틴을 다시 실행한다.
                    i -= 1;
                }
            }
        }

        return numStr;
    }


}
