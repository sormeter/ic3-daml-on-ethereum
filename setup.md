# Useful commands

## Starting DAML ledger (sandbox and navigator)
`daml start`

## Start navigator only
`daml navigator`

## Subscribe to grpc service -- ledgerId will be dynamic for your instance and when you first subscribe
### Pre-req : Install grpcurl via brew or apt-get or download relevant OS version from here https://github.com/fullstorydev/grpcurl/releases/tag/v1.3.0
`grpcurl -import-path . -plaintext -d '{"ledgerId" : "sandbox-ad95d846-56d4-4747-82b3-ad6625e515ca", "filter": {"filtersByParty":{"Operator":{}}}, "begin": {"boundary":0 } }' localhost:6865 com.digitalasset.ledger.api.v1.TransactionService.GetTransactionTrees`

## bin and abi for solidity smart contract
### Pre-req : Install Solidity compiler https://solidity.readthedocs.io/en/v0.5.3/installing-solidity.html
`solc solidity/Queue.sol --bin --abi --optimize -o solidity/build/`

## java codegen for solidity smart contract
### Pre-req : Install web3j command line : https://docs.web3j.io/command_line.html
`web3j solidity generate -b solidity/build/DamlTransactionQueue.bin -a solidity/build/DamlTransactionQueue.abi -o src/main/java -p com.daml.ethereum`
