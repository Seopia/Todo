import { faBackward } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useNavigate } from "react-router-dom";
import './CommonCSS.css'
import { useSelector } from "react-redux";
import { setStyle } from "./CommonFunction";

const BackButton = ({moveTo}) =>{
    const darkMode = useSelector(state => state.theme.darkMode);
    const nav = useNavigate();
    return(
        <div className='faBackword' onClick={()=>nav(moveTo)}>
                <FontAwesomeIcon icon={faBackward}/>
                <div style={{color:setStyle(darkMode,'text')}} className='back-button'>뒤로가기</div>
        </div>
    )
}

export default BackButton;