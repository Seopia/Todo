import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../../AxiosInterceptor";
import axios from "axios";

const FindAccount = () => {
    const nav = useNavigate();
    const [id, setId] = useState('');
    const [email,setEmail] = useState('');
    const [securityNumber, setSecurityNumber] = useState('');
    const [serverMsg, setServerMsg] = useState('');
    const [inputDisable, setInputDisable] = useState(false);

    const [time, setTime] = useState(600);

    const [confirm, setConfirm] = useState(false);
    //인증 번호 전송 버튼
    const findAccount = async () => {
        //id와 email이 일치하는 계정이 있는지 확인 후 인증번호 전송
        if(id && email){
            const form = new FormData();
            form.append('userId',id);
            form.append('userEmail',email);
            const res = await axios.post(`http://${process.env.REACT_APP_IP}/account/find-by-id-email`,form,{
                headers:{
                    "Content-Type": 'multipart/form-data',
                }
            });            
            if(res.data){
                setConfirm(true);
                setInputDisable(true);
                setServerMsg('');
            } else {
                setServerMsg('아이디랑 이메일이 일치하지 않아요');
            }
        } else {
            setServerMsg('모두 다 입력해주세요.');
        }
    }
    const confirmSecurityNumber = async () => {
        const form = new FormData();
        form.append('token',securityNumber);
        form.append('userId',id);
        form.append('userEmail',email);
        const res = await axios.post(`http://${process.env.REACT_APP_IP}/account/find-by-sc-num`,form,{
            headers:{
                "Content-Type": 'multipart/form-data',
            }
        });
        if(res.data){
            nav('/find-account/password-change',{state:{isValid: true,id:id,email:email}});
        } else {
            setServerMsg('인증번호가 일치하지 않아요.');
        }
    }
    useEffect(()=>{
        if(confirm){
            startTimer();
        }
    },[confirm])

    useEffect(()=>{    
            if(time > 0){
                console.log(time);
            } else {
                nav('/',{state:{message:'인증번호 시간이 만료되었습니다. 다시 시도해주세요.'}})
            }
    },[time])

    const startTimer = () => setInterval(()=>{
        setTime(prev => prev - 1);
    },1000)

    return(
        <section className="login-container">
            <div className="login-input-container">
                <div className="login-input">
                    <h1>비밀번호 찾기</h1>
                    {confirm ? time > 0 ? <div style={{color:'green'}}>남은 시간 : {time}초</div> : <div>제한 시간 종료</div> : <></>}
                    {serverMsg ? <div style={{color:'red'}}>{serverMsg}</div> : <></>}
                    <input disabled={inputDisable} className="login-id" placeholder="아이디를 입력해주세요" value={id} onChange={(e)=>setId(e.target.value)}/>
                    <input disabled={inputDisable} className="login-id" placeholder="이메일을 입력해주세요" value={email} onChange={(e)=>setEmail(e.target.value)}/>
                    
                    {confirm ? 
                    <>
                        <input value={securityNumber} onChange={(e)=>setSecurityNumber(e.target.value)} className="login-id" placeholder="인증번호를 입력해주세요"/>
                        <button onClick={confirmSecurityNumber} className="login-button">인증번호 확인</button>
                    </> : 
                    <button onClick={findAccount} className="login-button">인증번호 전송</button>
                    }
                </div>
            </div>
        </section>
    )
}

export default FindAccount;