import React, {useEffect, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";

import Box from "@material-ui/core/Box";
import PropTypes from "prop-types";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";

import {getQueryParams} from "./../components/Utils/PageUtils";

import SyncList from './Sync/SyncList';
import Setting from './Settings/index'
// import Backup from './backup/backupList'
// import Restore from './restore/restoreList'
// import RestoreIcon from '@material-ui/icons/Restore';
//import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';
//import BackupIcon from '@material-ui/icons/Backup';
import SettingsIcon from '@mui/icons-material/Settings';
import CloudSyncIcon from '@mui/icons-material/CloudSync';
import Websocket from './Sync/Websocket'; 
import NewList from './Sync/NewList'; 
//Dtate Picker package
Moment.locale("en");
momentLocalizer();

const useStyles = makeStyles((theme) => ({
  header: {
    fontSize: "20px",
    fontWeight: "bold",
    padding: "5px",
    paddingBottom: "10px",
  },
  inforoot: {
    margin: "5px",
  },

  dropdown: {
    marginTop :"50px"
   
  },
  paper: {
    marginRight: theme.spacing(2),
  },
  downmenu: {
    display: 'flex'
    },
}));






function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <Typography
      component="div"
      role="tabpanel"
      hidden={value !== index}
      id={`scrollable-force-tabpanel-${index}`}
      aria-labelledby={`scrollable-force-tab-${index}`}
      {...other}
    >
      {value === index && <Box p={5}>{children}</Box>}
    </Typography>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `scrollable-force-tab-${index}`,
    "aria-controls": `scrollable-force-tabpanel-${index}`,
  };
}
function HomePage(props) {
  const classes = useStyles();
  const [value, setValue] = useState(null);
  const getstate= props.location && props.location.state ? props.location.state : " " ;
  const urlIndex = getQueryParams("tab", props.location && props.location.search ? props.location.search : ""); 
  const urlTabs = urlIndex !== null ? urlIndex : getstate ;
  useEffect ( () => {

    switch(urlTabs){  
      case "database-sync": return setValue(0)
      case "setting": return setValue(1)

      default: return setValue(0)
    }
  }, [urlIndex]);
  
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };


  return (
    <>
     <div className="row page-titles mx-0" style={{marginTop:"0px", marginBottom:"-10px"}}>
			<ol className="breadcrumb">
				<li className="breadcrumb-item active"><h4>Central Sync</h4></li>
			</ol>
		  </div>
      <br/>
    <div className={classes.root} >
      <AppBar position="static" style={{backgroundColor:'#fff'}}>
        <Tabs
          value={value}
          onChange={handleChange}
          variant="scrollable"
          scrollButtons="on"
          indicatorColor="secondary"
          textColor="primary"
          aria-label="scrollable force tabs example"
        >
           <Tab className={classes.title} label="Generate & Upload JSON Files" icon={<CloudSyncIcon />} {...a11yProps(0)} />
         
          {/* <Tab className={classes.title} label="WebSocket  " icon={<SettingsIcon />} {...a11yProps(1)}/> 
          <Tab className={classes.title} label="Generate & Upload  " icon={<SettingsIcon />} {...a11yProps(2)}/>  */}
          <Tab className={classes.title} label="Configuration  " icon={<SettingsIcon />} {...a11yProps(1)}/> 
      </Tabs>
      </AppBar>

     
        <TabPanel value={value} index={0}>
          <SyncList />
        </TabPanel>
        <TabPanel value={value} index={1}>
          <Setting />
        </TabPanel>
       
     </div> 
    </>
  );
}



export default HomePage;
