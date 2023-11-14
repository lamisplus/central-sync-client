import React, {useState, useEffect} from 'react';
import { Modal, ModalHeader, ModalBody,Form,FormFeedback,
    Row,Col, Card,CardBody} from 'reactstrap';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { Alert } from 'reactstrap';
import { Spinner } from 'reactstrap';
import axios from "axios";
import { DropzoneArea } from 'material-ui-dropzone';
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';



const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    },
    cardBottom: {
        marginBottom: 20
    },
    Select: {
        height: 45,
        width: 350
    },
    button: {
        margin: theme.spacing(1)
    },

    root: {
        '& > *': {
            margin: theme.spacing(1)
        }
    },
    input: {
        display: 'none'
    }
}))


const GenerateKey = (props) => {
    const classes = useStyles()

    //const samplesdispatched ={"sampleManifests": [] };
    const GenerateKeyProcess = e => {
        e.preventDefault()
        alert("Processing ")
        //Closing of the modal 
        props.togglestatus();



    }



    return (
        <div >

            <Modal isOpen={props.showModal} toggle={props.toggleModal} className={props.className} size="lg" backdrop={false} backdrop="static">
                <Form >
                    <ModalHeader toggle={props.toggleModal}>GENERATE KEY</ModalHeader>
                    <ModalBody>

                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={12} >

                                        <Alert color="primary" >
                                            <p style={{marginTop: '.7rem' }}>
                                                Info : &nbsp;&nbsp;&nbsp;<span style={{ fontWeight: 'bolder'}}>{'Kindly copy the generated key for upload'}</span>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                            </p>

                                        </Alert>
                                    </Col>

                                    <Col md={6}>

                                    </Col>
                                </Row>
                                <br/>

                                <br/>



                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={props.togglestatus}
                                    className={classes.button}
                                    startIcon={<CancelIcon />}
                                >
                                    Cancel
                                </MatButton>
                            </CardBody>
                        </Card>
                    </ModalBody>

                </Form>
            </Modal>
        </div>
    );
}

export default GenerateKey;
