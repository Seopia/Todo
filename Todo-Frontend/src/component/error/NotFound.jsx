import { useSelector } from "react-redux";
import { setStyle } from "../common/CommonFunction";

const NotFound = () => {
    const darkMode = useSelector((state)=>state.theme.darkMode);
    return(
        <div style={{color:setStyle(darkMode,'text')}}>존재하지 않는 페이지</div>
    )
}

export default NotFound;