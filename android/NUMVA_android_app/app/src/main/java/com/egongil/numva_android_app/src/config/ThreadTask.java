package com.egongil.numva_android_app.src.config;

public abstract class ThreadTask<T1, T2> implements Runnable {
    T1 mArgument;
    T2 mResult;

    final public void execute(final T1 arg){
        mArgument = arg;
        Thread thread = new Thread(this);
        thread.start();

        try {
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
            onPostExecute(null);
            return;
        }
        onPostExecute(mResult);
    }

    @Override
    public void run() {
        mResult = doInBackground(mArgument);
    }

    protected abstract T2 doInBackground(T1 arg);

    protected abstract void onPostExecute(T2 Result);
}
