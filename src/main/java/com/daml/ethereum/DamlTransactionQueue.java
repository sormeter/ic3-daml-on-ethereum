package com.daml.ethereum;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class DamlTransactionQueue extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161058e38038061058e8339818101604052602081101561003357600080fd5b5051600055610547806100476000396000f3fe60806040526004361061003f5760003560e01c80631aa3a00814610044578063aff5edb11461004e578063c3c5a54714610063578063ef7fa71b146100aa575b600080fd5b61004c610139565b005b34801561005a57600080fd5b5061004c6101ce565b34801561006f57600080fd5b506100966004803603602081101561008657600080fd5b50356001600160a01b03166102e3565b604080519115158252519081900360200190f35b3480156100b657600080fd5b50610127600480360360208110156100cd57600080fd5b8101906020810181356401000000008111156100e857600080fd5b8201836020820111156100fa57600080fd5b8035906020019184600183028401116401000000008311171561011c57600080fd5b509092509050610340565b60408051918252519081900360200190f35b6000543414158061014e575061014e336102e3565b1561018a5760405162461bcd60e51b81526004018080602001828103825260338152602001806104e06033913960400191505060405180910390fd5b600280546001810182556000919091527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace0180546001600160a01b03191633179055565b60005b6002548110156102df57336001600160a01b0316600282815481106101f257fe5b6000918252602090912001546001600160a01b031614156102d75760028054600019810190811061021f57fe5b600091825260209091200154600280546001600160a01b03909216918390811061024557fe5b9060005260206000200160006101000a8154816001600160a01b0302191690836001600160a01b03160217905550600280548061027e57fe5b600082815260208120820160001990810180546001600160a01b03191690559091019091558054604051339282156108fc02929190818181858888f193505050501580156102d0573d6000803e3d6000fd5b50506102e1565b6001016101d1565b505b565b6000805b60025481101561033557826001600160a01b03166002828154811061030857fe5b6000918252602090912001546001600160a01b0316141561032d57600191505061033b565b6001016102e7565b50600090505b919050565b600061034b336102e3565b610394576040805162461bcd60e51b815260206004820152601560248201527414d95b99195c881b9bdd081c9959da5cdd195c9959605a1b604482015290519081900360640190fd5b60015460408051828152602081018281529181018590527fdbc166fa72fa2c59d677120fbe1a427f98c81484c9b62f1c8c2d3f98affd454892918691869160608201848480828437600083820152604051601f909101601f1916909201829003965090945050505050a16001805480820180835560008390529061043b907fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6018686610444565b50039392505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104855782800160ff198235161785556104b2565b828001600101855582156104b2579182015b828111156104b2578235825591602001919060010190610497565b506104be9291506104c2565b5090565b6104dc91905b808211156104be57600081556001016104c8565b9056fe496e73756666696369656e7420726567697374726174696f6e20666565206f7220616c72656164792072656769737465726564a265627a7a72305820aec477a9eb3f4a96100a2d87b417b8de3b98cebe8169e1467af9e8994820f28264736f6c63430005090032";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_DEREGISTER = "deregister";

    public static final String FUNC_ISREGISTERED = "isRegistered";

    public static final String FUNC_SUBMIT = "submit";

    public static final Event REQUEST_EVENT = new Event("Request", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected DamlTransactionQueue(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DamlTransactionQueue(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DamlTransactionQueue(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DamlTransactionQueue(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> register(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> deregister() {
        final Function function = new Function(
                FUNC_DEREGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isRegistered(String addr) {
        final Function function = new Function(FUNC_ISREGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> submit(byte[] damlTxn) {
        final Function function = new Function(
                FUNC_SUBMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(damlTxn)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<RequestEventResponse> getRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REQUEST_EVENT, transactionReceipt);
        ArrayList<RequestEventResponse> responses = new ArrayList<RequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RequestEventResponse typedResponse = new RequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.payload = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RequestEventResponse> requestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RequestEventResponse>() {
            @Override
            public RequestEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REQUEST_EVENT, log);
                RequestEventResponse typedResponse = new RequestEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.payload = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RequestEventResponse> requestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REQUEST_EVENT));
        return requestEventFlowable(filter);
    }

    @Deprecated
    public static DamlTransactionQueue load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DamlTransactionQueue(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DamlTransactionQueue load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DamlTransactionQueue(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DamlTransactionQueue load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DamlTransactionQueue(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DamlTransactionQueue load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DamlTransactionQueue(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DamlTransactionQueue> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger regFee) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(regFee)));
        return deployRemoteCall(DamlTransactionQueue.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<DamlTransactionQueue> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger regFee) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(regFee)));
        return deployRemoteCall(DamlTransactionQueue.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DamlTransactionQueue> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger regFee) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(regFee)));
        return deployRemoteCall(DamlTransactionQueue.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DamlTransactionQueue> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger regFee) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(regFee)));
        return deployRemoteCall(DamlTransactionQueue.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class RequestEventResponse {
        public Log log;

        public BigInteger id;

        public byte[] payload;
    }
}
