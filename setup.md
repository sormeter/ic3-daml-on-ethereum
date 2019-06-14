# Useful commands

## Starting DAML ledger (sandbox and navigator)
`daml start`

## Start navigator only
`daml navigator`

## Subscribe to grpc service -- ledgerId will be dynamic for your instance and when you first subscribe
### Pre-req : Install grpcurl via brew or apt-get or download relevant OS version from here https://github.com/fullstorydev/grpcurl/releases/tag/v1.3.0
`grpcurl -import-path . -plaintext -d '{"ledgerId" : "sandbox-ad95d846-56d4-4747-82b3-ad6625e515ca", "filter": {"filtersByParty":{"Operator":{}}}, "begin": {"boundary":0 } }' localhost:6865 com.digitalasset.ledger.api.v1.TransactionService.GetTransactionTrees`
