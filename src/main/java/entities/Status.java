package entities;

public enum Status {
    CREATED,  //  just created by user
    INWORK, // if taxi found but not submitted by client
    AGREE,   //  if client gave agreement
    REJECTED,  // rejected by any user
    DONE   // completed
}
