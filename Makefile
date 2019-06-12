network-dir	:=	network
network-file	:=	$(network-dir)/network.name
password-file	:=	$(network-dir)/password
miner-threads	:=	1


# Ethereum bits
ethereum-setup-node:
	mkdir -pv $(network-dir)
	geth init genesis.json --datadir $(network-dir)/node0
	geth --datadir $(network-dir)/node0 account new

ethereum-start-node:
	geth --datadir $(network-dir)/node0 --networkid 2019061016 \
		--port 30303 --rpc --rpcapi 'db,personal,eth,net,web3,debug' \
		--rpccorsdomain='*' --rpcaddr='localhost' --rpcport 8545 \
		--mine --miner.threads $(miner-threads) --ipcpath $(network-dir)/node0/geth.ipc \
		2>  $(network-dir)/node0.log
		
ethereum-stop-node:
	kill -INT $(shell ps -auf | grep [g]eth | grep 2019061016 | awk '{print $$2}')

ethereum-connect-client:
	geth attach --datadir $(network-dir)/node0

ethereum-cleanup:
	rm -rfv $(network-dir)
