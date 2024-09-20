export const toggleDarkMode = () => ({
    type: 'TOGGLE_DARK_MODE',
});
const theme = localStorage.getItem('DarkTheme');
const initialState = {
    darkMode: true,  
};


export const themeReducer = (state = initialState, action) => {
    switch(action.type) {
        case 'TOGGLE_DARK_MODE':                        
            return{
                ...state,
                darkMode: !state.darkMode,
            };
        default:
            return state;
    }
};