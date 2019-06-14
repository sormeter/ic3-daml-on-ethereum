pragma solidity >= 0.5.1 <0.6.0;

contract DamlTransactionQueue {
    event Request(uint id, bytes payload);

    uint REGISTRATION_FEE;

    bytes[] txnRequests;
    address[] registeredAddrs;

    constructor(uint regFee) public {
        REGISTRATION_FEE = regFee;
    }

    function isRegistered(address addr) public view returns (bool) {
        for (uint i = 0; i < registeredAddrs.length; i++) {
            if (registeredAddrs[i] == addr) return true;
        }
        return false;
    }

    function register() external payable {
        if (msg.value != REGISTRATION_FEE || isRegistered(msg.sender)) revert("Insufficient registration fee or already registered");

        registeredAddrs.push(msg.sender);
    }

    function deregister() external {
        for (uint i = 0; i < registeredAddrs.length; i++) {
            if (registeredAddrs[i] == msg.sender) {
                registeredAddrs[i] = registeredAddrs[registeredAddrs.length - 1];
                registeredAddrs.pop();
                msg.sender.transfer(REGISTRATION_FEE);
                return;
            }
        }
    }

    // Submits a new DAML transaction request and returns the index of that request.
    function submit(bytes calldata damlTxn) external returns(uint) {
        if (!isRegistered(msg.sender)) revert("Sender not registered");

        emit Request(txnRequests.length, damlTxn);
        return txnRequests.push(damlTxn) - 1;
    }
}
