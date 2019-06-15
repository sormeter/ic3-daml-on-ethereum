pragma solidity >= 0.5.1 <0.6.0;

contract DamlTransactionQueue {
    event Request(uint id, bytes payload);

    uint REGISTRATION_FEE;

    bytes[] txnRequests;
    mapping(address => bool) registeredAddrs;

    constructor(uint regFee) public {
        REGISTRATION_FEE = regFee;
    }

    function register() external payable {
        if (msg.value != REGISTRATION_FEE || !registeredAddrs[msg.sender]) revert("Incorrect registration fee or already registered");
        registeredAddrs[msg.sender] = true;
    }

    function deregister() external {
        if (registeredAddres[msg.sender]) {
            registeredAddrs[msg.sender] = false;
            msg.sender.transfer(REGISTRATION_FEE);
        }
    }

    // Submits a new DAML transaction request and returns the index of that request.
    function submit(bytes calldata damlTxn) external returns(uint) {
        if (!registeredAddrs[msg.sender]) revert("Sender not registered");

        uint index = txnRequests.length;
        emit Request(index, damlTxn);
        txnRequests.push(damlTxn);
        return index;
    }
}
