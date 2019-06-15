# ic3-daml-on-ethereum

Cornell IC3 hackathon implementing an integrated DAML runtim on an Ethereum private network.

## Ethereum stuff (`Makefile` included) - in order

Setup an Ethereum private network with (hard-coded) 4 nodes

`make ethereum-setup-nodes`

Run all Ethereum nodes, unlock Etherbase account, and start mining

`make ethereum-run-network`

Connect to the JS console

`make ethereum-connect-client node=NODE`

where `NODE` is {0..3}

Stop everything

`make ethereum-stop-network`

Clean up the Ethereum network, deleting all keys, files, DB, etc.

`make ethereum-cleanup`

Networking side of things:

| Node | RPC Port | Listening port |
| --- | --- | --- |
| Node0 | 8450 | 30300 |
| Node1 | 8451 | 30301 |
| Node2 | 8452 | 30302 |
| Node3 | 8453 | 30303 |

___

## Geth Console

Create a new account

```
geth --datadir network/node0 account new
Passphrase:
Repeat passphrase:`
Address: {eb49b0e5cc0a631054ee77a3edbbbba010dfb1ee}
```

List accounts

```
> eth.accounts
["0x887395b8a879cf538fbbb2236e28e8d2b12288f9", "0xeb49b0e5cc0a631054ee77a3edbbbba010dfb1ee"]
```

Send Ether from accounts[0] -> accounts[1]

```
> eth.sendTransaction({from: eth.accounts[0], to: eth.accounts[1], value: web3.toWei(1000, "ether")})
"0x045a60b37a084594dac7faa6432b6a92e8235d62b1850af39ad98cfcb59f3799"
```

Send Ether and Payload from accounts[0] -> accounts[1]

```
> eth.sendTransaction({from: eth.accounts[0], to: eth.accounts[1], value: web3.toWei(10, "ether"), data: web3.toHex("you go to jail, bad boy!!")})
"0xe73fcf1961709cfd77e48704ab8a33dc31e6ddccf4b7ba0d681ab39cf12974ff"
```

Get receipt for the transaction

```
> eth.getTransactionReceipt("0xe73fcf1961709cfd77e48704ab8a33dc31e6ddccf4b7ba0d681ab39cf12974ff")
{
  blockHash: "0x4972c60f8d6b46d9371f75aa067f7400121d04a40217efbb762ec4cfe8345b73",
  blockNumber: 4500,
  contractAddress: null,
  cumulativeGasUsed: 22700,
  from: "0x887395b8a879cf538fbbb2236e28e8d2b12288f9",
  gasUsed: 22700,
  logs: [],
  logsBloom: "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
  root: "0x402a80877f6b4bba60db27b649552aaa043ec2d6aa21b7c2aca8cb081b5bf296",
  to: "0xeb49b0e5cc0a631054ee77a3edbbbba010dfb1ee",
  transactionHash: "0xe73fcf1961709cfd77e48704ab8a33dc31e6ddccf4b7ba0d681ab39cf12974ff",
  transactionIndex: 0
}
```

Unpack the transaction

```
> eth.getTransaction("0xe73fcf1961709cfd77e48704ab8a33dc31e6ddccf4b7ba0d681ab39cf12974ff")
{
  blockHash: "0x4972c60f8d6b46d9371f75aa067f7400121d04a40217efbb762ec4cfe8345b73",
  blockNumber: 4500,
  from: "0x887395b8a879cf538fbbb2236e28e8d2b12288f9",
  gas: 90000,
  gasPrice: 1000000000,
  hash: "0xe73fcf1961709cfd77e48704ab8a33dc31e6ddccf4b7ba0d681ab39cf12974ff",
  input: "0x796f7520676f20746f206a61696c2c2062616420626f792121",
  nonce: 26,
  r: "0x4920e33ba322a99277ab6947a389e10c2d70cd5eae3aa7493c5ef80cbc703cce",
  s: "0x7e9af3af0ddc9b3a105b8b89b74f085db89f095c15b5bb113e3c9d0e6fdaec1a",
  to: "0xeb49b0e5cc0a631054ee77a3edbbbba010dfb1ee",
  transactionIndex: 0,
  v: "0xf0b0da54",
  value: 10000000000000000000
}
```

Get the hash of the payload

```
> eth.getTransaction("0xb8829f15b4d83e0c989c9f14e8b1bae282d25f9439464a0edfaf1e28876a0685").input
"0x66756b6e207061796c6f6164"
```

Get the payload itself

```
> web3.toAscii(eth.getTransaction("0xe73fcf1961709cfd77e48704ab8a33dc31e6ddccf4b7ba0d681ab39cf12974ff").input)
"you go to jail, bad boy!!"
```

___

## Testing the DAML Workflows

Starting DAML ledger (sandbox and navigator) for testing; this will load up the partial scenario wherein the three primary users + `ETH/USD` and `ETH/EUR` currency pairs have been registered.

From the top-level directory, run:

```
daml start
```

This will also spin up a Navigator instance that can be used to drive the workflows forward.

___

## Other DAML stuff

Start navigator only

```
daml navigator
```

Subscribe to grpc service -- ledgerId will be dynamic for your instance and when you first subscribe
Pre-req : Install grpcurl via brew or download relevant OS version from [here](https://github.com/fullstorydev/grpcurl/releases/tag/v1.3.0)

```
grpcurl -import-path . -plaintext -d '{"ledgerId" : "sandbox-ad95d846-56d4-4747-82b3-ad6625e515ca", "filter": {"filtersByParty":{"Operator":{}}}, "begin": {"boundary":0 } }' localhost:6865 com.digitalasset.ledger.api.v1.TransactionService.GetTransactionTrees
```

`BIN`s and `ABI`s for solidity smart contract
Pre-req : [Install Solidity compiler](https://solidity.readthedocs.io/en/v0.5.3/installing-solidity.html)

```
solc solidity/Queue.sol --bin --abi --optimize -o solidity/build/
```

Alternatively just generate wrappers by calling the Java class directly

```
org.web3j.codegen.SolidityFunctionWrapperGenerator -b /path/to/<smart-contract>.bin -a /path/to/<smart-contract>.abi -o src/main/java -p com.daml.ethereum
```

Java codegen for solidity smart contract
Pre-req : [Install web3j command line](https://docs.web3j.io/command_line.html)

```
web3j solidity generate -b solidity/build/DamlTransactionQueue.bin -a solidity/build/DamlTransactionQueue.abi -o src/main/java -p com.daml.ethereum
```
